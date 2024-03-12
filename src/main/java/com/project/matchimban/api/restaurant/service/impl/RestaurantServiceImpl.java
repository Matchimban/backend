package com.project.matchimban.api.restaurant.service.impl;

import com.project.matchimban.api.auth.security.model.CustomUserDetails;
import com.project.matchimban.api.restaurant.domain.dto.request.MenuCreateRequest;
import com.project.matchimban.api.restaurant.domain.dto.request.RestaurantCreateRequest;
import com.project.matchimban.api.restaurant.domain.dto.request.RestaurantUpdateRequest;
import com.project.matchimban.api.restaurant.domain.dto.response.RestaurantDetailReadResponse;
import com.project.matchimban.api.restaurant.domain.dto.response.RestaurantReadResponse;
import com.project.matchimban.api.restaurant.domain.entity.Menu;
import com.project.matchimban.api.restaurant.domain.entity.MenuImage;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;
import com.project.matchimban.api.restaurant.domain.entity.RestaurantImage;
import com.project.matchimban.api.restaurant.repository.MenuImageRepository;
import com.project.matchimban.api.restaurant.repository.MenuRepository;
import com.project.matchimban.api.restaurant.repository.RestaurantImageRepository;
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
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final UserRepository userRepository;

    private final RestaurantRepository restaurantRepository;
    private final RestaurantImageRepository restaurantImageRepository;
    private final MenuRepository menuRepository;
    private final MenuImageRepository menuImageRepository;
    private final RestaurantRepositoryQuerydsl restaurantRepositoryQuerydsl;

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

//    public void createRestaurantImage(List<RestaurantImageCreateRequest> imageInfo, Restaurant restaurant) {
//        List<RestaurantImage> images = new ArrayList<>();
//        for (RestaurantImageCreateRequest request : imageInfo) {
//            Optional<FileInfo> fileInfo = s3Service.saveFile(request.getImage());
//
//            if (fileInfo.isEmpty())
//                throw new SVCException(ErrorConstant.FILE_ERROR_NULL_FILE);
//
//            RestaurantImage image = RestaurantImage.createRestaurantImage(restaurant, request, fileInfo.get());
//            images.add(image);
//        }
//        restaurantImageRepository.saveAll(images);
//    }

    public void createRestaurantMenu(List<MenuCreateRequest> menuInfo, Restaurant restaurant) {
        List<Menu> menus = new ArrayList<>();
        List<MenuImage> menuImages = new ArrayList<>();
        for (MenuCreateRequest request : menuInfo) {
            Optional<FileInfo> fileInfo = s3Service.saveFile(request.getImage());

            if (fileInfo.isEmpty())
                throw new SVCException(ErrorConstant.FILE_ERROR_NULL_FILE);

            MenuImage menuImage = MenuImage.createMenuImage(fileInfo.get());
            Menu menu = Menu.createMenu(restaurant, request, menuImage);

            menuImages.add(menuImage);
            menus.add(menu);
        }

        menuImageRepository.saveAll(menuImages);
        menuRepository.saveAll(menus);
    }

    public List<RestaurantReadResponse> getRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream()
                .map(RestaurantReadResponse::new)
                .collect(Collectors.toList());
    }

    public RestaurantDetailReadResponse getRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow();
        return RestaurantDetailReadResponse.createRestaurantDetailReadResponse(restaurant);
    }

    @Transactional
    public void updateRestaurant(Long id, RestaurantUpdateRequest request) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow();

        Address address = Address.createAddress(
                "eeee", "eee", "e", "ee", 1.1, 1.1
        );
        restaurant.updateRestaurant(request, address);
        restaurantRepository.save(restaurant);
    }
}