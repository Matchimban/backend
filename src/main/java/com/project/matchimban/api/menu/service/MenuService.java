package com.project.matchimban.api.menu.service;

import com.project.matchimban.api.menu.domain.dto.request.MenuCreateRequest;
import com.project.matchimban.api.menu.domain.dto.response.MenusReadResponse;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;

import java.util.List;

public interface MenuService {
    void createMenu(Restaurant restaurant, MenuCreateRequest request);

    List<MenusReadResponse> getMenus(Restaurant restaurant);
}
