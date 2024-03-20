package com.project.matchimban.api.menu.service;

import com.project.matchimban.api.menu.dto.dto.MenuCreateRequest;
import com.project.matchimban.api.menu.dto.dto.MenuReadResponse;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;

import java.util.List;

public interface MenuService {
    void createMenu(Restaurant restaurant, MenuCreateRequest request);

    List<MenuReadResponse> getMenus(Restaurant restaurant);
}
