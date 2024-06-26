package com.project.matchimban.api.restaurant.service.impl;

import com.project.matchimban.api.auth.security.model.CustomUserDetails;
import com.project.matchimban.api.restaurant.domain.dto.request.RestaurantCreateRequest;
import com.project.matchimban.api.restaurant.domain.dto.request.RestaurantStatusUpdateRequest;
import com.project.matchimban.api.restaurant.domain.dto.request.RestaurantUpdateRequest;
import com.project.matchimban.api.restaurant.domain.dto.response.RestaurantImageReadResponse;
import com.project.matchimban.api.restaurant.domain.dto.response.RestaurantReadResponse;
import com.project.matchimban.api.restaurant.domain.dto.response.RestaurantsReadResponse;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;
import com.project.matchimban.api.restaurant.domain.entity.RestaurantImage;
import com.project.matchimban.api.restaurant.domain.enums.RestaurantStatus;
import com.project.matchimban.api.restaurant.repository.RestaurantImageRepository;
import com.project.matchimban.api.restaurant.repository.RestaurantImageRepositoryQuerydsl;
import com.project.matchimban.api.restaurant.repository.RestaurantRepository;
import com.project.matchimban.api.restaurant.repository.RestaurantRepositoryQuerydsl;
import com.project.matchimban.api.restaurant.service.RestaurantService;
import com.project.matchimban.api.user.domain.entity.User;
import com.project.matchimban.api.user.repository.UserRepository;
import com.project.matchimban.common.exception.ErrorConstant;
import com.project.matchimban.common.exception.SVCException;
import com.project.matchimban.common.global.Address;
import com.project.matchimban.common.global.FileInfo;
import com.project.matchimban.common.modules.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.project.matchimban.api.user.domain.enums.UserRole.ROLE_OWNER;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final UserRepository userRepository;

    private final RestaurantRepository restaurantRepository;
    private final RestaurantImageRepository restaurantImageRepository;
    private final RestaurantRepositoryQuerydsl restaurantRepositoryQuerydsl;
    private final RestaurantImageRepositoryQuerydsl restaurantImaageRepositoryQuerydsl;

    private final S3Service s3Service;

    @Transactional
    public void registerRestaurant(RestaurantCreateRequest request, CustomUserDetails userDetails) {
        User user = userRepository.findById(userDetails.getUserId())
                .orElseThrow(() -> new SVCException(ErrorConstant.NOT_FOUND_USER));
        // 만약 user가 null이라면? NullPointerException 발생

        Restaurant restaurant = createRestaurant(request, user);
        createRestaurantImage(restaurant, request.getImages());
//        createRestaurantImage(request.getImages(), restaurant);
//        createRestaurantMenu(request.getMenus(), restaurant);
    }

    public Restaurant createRestaurant(RestaurantCreateRequest request, User user) {
        Address address = Address.createAddress(
                request.getAddrSido(),
                request.getAddrSigg(),
                request.getAddrEmd(),
                request.getAddrDetail(),
                request.getLatitude(),
                request.getLongitude()
        );

        Restaurant restaurant = Restaurant.createRestaurant(request, user, address);
        restaurantRepository.save(restaurant);
        return restaurant;
    }

    public void createRestaurantImage(Restaurant restaurant, List<MultipartFile> images) {
        List<RestaurantImage> imagesToSave = new ArrayList<>();

        if (images.size() == 0)
            throw new SVCException(ErrorConstant.FILE_ERROR_NULL_FILE);

        MultipartFile mainImage = images.remove(0);
        Optional<FileInfo> mainFileInfo = s3Service.saveFile(mainImage);
        RestaurantImage main = RestaurantImage.createMainRestaurantImage(restaurant, mainFileInfo.get());
        imagesToSave.add(main);

        for (MultipartFile file : images) {
            Optional<FileInfo> fileInfo = s3Service.saveFile(file);

            if (fileInfo.isEmpty())
                throw new SVCException(ErrorConstant.FILE_ERROR_NULL_FILE);

            RestaurantImage image = RestaurantImage.createSubRestaurantImage(restaurant, fileInfo.get());
            imagesToSave.add(image);
        }
        restaurantImageRepository.saveAll(imagesToSave);
    }

    public List<RestaurantsReadResponse> getRestaurants() {
        return restaurantRepositoryQuerydsl.getRestaurantsLeftJoinImage();
    }

    public RestaurantReadResponse getRestaurant(Long id) {
        Restaurant restaurant = validateRestaurantId(id);
        List<RestaurantImageReadResponse> images = restaurantImaageRepositoryQuerydsl.getRestaurantImageByRestaurantId(restaurant);
        return RestaurantReadResponse.createRestaurantDetailReadResponse(restaurant, images);
    }

    @Transactional
    public void updateRestaurant(Long id, RestaurantUpdateRequest request) {
        Restaurant restaurant = validateRestaurantId(id);

        Address address = Address.createAddress(
                request.getAddrSido(), request.getAddrSigg(), request.getAddrEmd(), request.getAddrDetail(), request.getLatitude(), request.getLongitude()
        );
        restaurant.updateRestaurant(request, address);
        restaurantRepository.save(restaurant);
    }

    public Restaurant validateRestaurantId(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new SVCException(ErrorConstant.RESTAURANT_ERROR_NOT_FOUND_RESTAURANT));
    }

    @Transactional
    public void changeRestaurantStatus(Long id, RestaurantStatusUpdateRequest dto, CustomUserDetails userDetails) {
        User user = userRepository.findById(userDetails.getUserId())
                .orElseThrow(() -> new SVCException(ErrorConstant.NOT_FOUND_USER));

        Restaurant restaurant = validateRestaurantId(id);

        RestaurantStatus status = dto.getStatus();
        if (status.equals(RestaurantStatus.INVISIBLE)
                || status.equals(RestaurantStatus.PUBLISHED))
            restaurant.changeStatus(dto.getStatus());
        else if (status.equals(RestaurantStatus.DELETED))
            deleteRestaurant(user, restaurant);
        else
            throw new SVCException(ErrorConstant.RESTAURANT_ERROR_INVALID_STATUS);
    }

    public void deleteRestaurant(User user, Restaurant restaurant) {
        if (user.getUserRole().equals(ROLE_OWNER)) {
            if (user.getId() != restaurant.getUser().getId())
                throw new SVCException(ErrorConstant.NOT_OWNED_BY_THE_USER);
        }

        restaurant.changeStatus(RestaurantStatus.DELETED);
    }
}