package com.project.matchimban.api.restaurant.service.impl;

import com.project.matchimban.api.auth.security.model.CustomUserDetails;
import com.project.matchimban.api.restaurant.domain.dto.MenuCreateRequest;
import com.project.matchimban.api.restaurant.domain.dto.RestaurantCreateRequest;
import com.project.matchimban.api.restaurant.domain.dto.RestaurantImageCreateRequest;
import com.project.matchimban.api.restaurant.domain.dto.RestaurantRegisterRequest;
import com.project.matchimban.api.restaurant.domain.entity.Menu;
import com.project.matchimban.api.restaurant.domain.entity.MenuImage;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;
import com.project.matchimban.api.restaurant.domain.entity.RestaurantImage;
import com.project.matchimban.api.restaurant.repository.RestaurantImageRepository;
import com.project.matchimban.api.restaurant.service.RestaurantService;
import com.project.matchimban.api.user.domain.entity.User;
import com.project.matchimban.api.user.repository.UserRepository;
import com.project.matchimban.common.exception.ErrorConstant;
import com.project.matchimban.common.exception.SVCException;
import com.project.matchimban.common.global.Address;
import com.project.matchimban.common.global.FileInfo;
import com.project.matchimban.common.modules.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantImageRepository restaurantImageRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    @Transactional
    public void registerRestaurant(RestaurantRegisterRequest request, CustomUserDetails userDetails) {
        User user = userRepository.findById(userDetails.getUserId())
                .orElseThrow(() -> new SVCException(ErrorConstant.NOT_FOUND_USER));

        Restaurant restaurant = createRestaurant(request.getRestaurant(), user);
        createRestaurantImage(request.getImages(), restaurant);
        createRestaurantMenu(request.getMenus(), restaurant);
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

        return Restaurant.createRestaurant(request, user, address);
    }

    public void createRestaurantImage(List<RestaurantImageCreateRequest> images, Restaurant restaurant) {
        for (RestaurantImageCreateRequest request : images) {
            Optional<FileInfo> fileInfo = s3Service.saveFile(request.getFile());

            if (fileInfo.isEmpty())
                throw new SVCException(ErrorConstant.FILE_ERROR_NULL_FILE);

            RestaurantImage image = RestaurantImage.createRestaurantImage(restaurant, request, fileInfo.get());
            restaurantImageRepository.save(image);
            // saveAll로 한꺼번에 하면 성능 개선 가능
        }
    }

    public void createRestaurantMenu(List<MenuCreateRequest> menus, Restaurant restaurant) {
        for (MenuCreateRequest request : menus) {
            Optional<FileInfo> fileInfo = s3Service.saveFile(request.getImage());

            if (fileInfo.isEmpty())
                throw new SVCException(ErrorConstant.FILE_ERROR_NULL_FILE);

            MenuImage menuImage = MenuImage.createMenuImage(fileInfo.get());
            Menu menu = Menu.createMenu(restaurant, request, menuImage);
            // saveAll로 한꺼번에 하면 성능 개선 가능
        }
    }
}
