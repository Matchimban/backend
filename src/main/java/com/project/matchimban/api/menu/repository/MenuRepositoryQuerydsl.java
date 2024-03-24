package com.project.matchimban.api.menu.repository;

import com.project.matchimban.api.menu.domain.dto.response.MenusReadResponse;

import java.util.List;

public interface MenuRepositoryQuerydsl {
    List<MenusReadResponse> getMenusLeftJoinMenuImage(Long restaurantId);
}
