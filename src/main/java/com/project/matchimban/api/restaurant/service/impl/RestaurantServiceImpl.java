package com.project.matchimban.api.restaurant.service.impl;

import com.amazonaws.services.s3.internal.S3AbortableInputStream;
import com.project.matchimban.Test;
import com.project.matchimban.api.auth.security.model.CustomUserDetails;
import com.project.matchimban.api.restaurant.domain.dto.MenuCreateRequest;
import com.project.matchimban.api.restaurant.domain.dto.RestaurantCreateRequest;
import com.project.matchimban.api.restaurant.domain.dto.RestaurantImageCreateRequest;
import com.project.matchimban.api.restaurant.domain.dto.RestaurantRegisterRequest;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;
import com.project.matchimban.api.restaurant.domain.entity.RestaurantImage;
import com.project.matchimban.api.restaurant.repository.RestaurantRepository;
import com.project.matchimban.api.restaurant.service.RestaurantService;
import com.project.matchimban.api.user.domain.entity.User;
import com.project.matchimban.api.user.repository.UserRepository;
import com.project.matchimban.api.wishlist.domain.Wishlist;
import com.project.matchimban.common.exception.ErrorConstant;
import com.project.matchimban.common.exception.SVCException;
import com.project.matchimban.common.global.Address;
import com.project.matchimban.common.modules.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
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

        Restaurant restaurant = Restaurant.createRestaurant(request, user, address);
        return restaurant;
    }

    public void createRestaurantImage(List<RestaurantImageCreateRequest> images, Restaurant restaurant) {
        for (RestaurantImageCreateRequest request : images) {
            //s3Service.uploadFile();
            RestaurantImage image = RestaurantImage.createRestaurantImage(restaurant, request);

        }
    }

    public void createRestaurantMenu(List<MenuCreateRequest> menus, Restaurant restaurant) {

    }

    public void registerRestaurantImage(Long restaurantId, List<RestaurantImageCreateRequest> images) {

    }
}
