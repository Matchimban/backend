package com.project.matchimban.api.restaurant.service.impl;

import com.project.matchimban.api.auth.security.model.CustomUserDetails;
import com.project.matchimban.api.restaurant.domain.dto.RestaurantCreateRequest;
import com.project.matchimban.api.restaurant.domain.dto.RestaurantImageCreateRequest;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;
import com.project.matchimban.api.restaurant.domain.entity.RestaurantImage;
import com.project.matchimban.api.restaurant.repository.RestaurantRepository;
import com.project.matchimban.api.restaurant.service.RestaurantService;
import com.project.matchimban.api.user.domain.entity.User;
import com.project.matchimban.api.user.repository.UserRepository;
import com.project.matchimban.common.exception.ErrorConstant;
import com.project.matchimban.common.exception.SVCException;
import com.project.matchimban.common.global.Address;
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

    public void createRestaurant(RestaurantCreateRequest dto, CustomUserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new SVCException(ErrorConstant.NOT_FOUND_USER));

        Address address = Address.createAddress(
                dto.getAddrSido(),
                dto.getAddrSigg(),
                dto.getAddrEmd(),
                dto.getAddrDetail(),
                dto.getLatitude(),
                dto.getLongitude()
        );

        Restaurant.createRestaurant(dto, user, address);
    }

    public void registerRestaurantImage(Long id, List<RestaurantImageCreateRequest> images) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new SVCException(ErrorConstant.RESTAURANT_ERROR_NOT_FOUND_RESTAURANT));

//        // 저장
//        for (RestaurantImageCreateRequest request : images) {
//            RestaurantImage image = RestaurantImage.createRestaurantImage(restaurant, request);
//
//        }
    }
}
