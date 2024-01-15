package com.project.matchimban.api.restaurant.service.impl;

import com.project.matchimban.api.restaurant.domain.dto.RestaurantCreateRequest;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;
import com.project.matchimban.api.restaurant.repository.RestaurantRepository;
import com.project.matchimban.api.restaurant.service.RestaurantService;
import com.project.matchimban.api.user.domain.entity.User;
import com.project.matchimban.api.user.repository.UserRepository;
import com.project.matchimban.common.exception.ErrorConstant;
import com.project.matchimban.common.exception.SVCException;
import com.project.matchimban.common.global.Address;
import com.project.matchimban.common.response.ResultData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public ResponseEntity<Object> createRestaurant(RestaurantCreateRequest dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new SVCException(ErrorConstant.NOT_FOUND_USER));

        Address address = Address.createAddress(
                dto.getAddrSido(),
                dto.getAddrSigg(),
                dto.getAddrEmd(),
                dto.getAddrDetail(),
                dto.getLatitude(),
                dto.getLongitude()
        );

        Restaurant restaurant = Restaurant.createRestaurant(dto, user, address);

        restaurantRepository.save(restaurant);

        return new ResponseEntity<>(new ResultData(), HttpStatus.OK);
    }
}
