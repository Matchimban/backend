package com.project.matchimban.api.menu.repository.impl;

import com.project.matchimban.api.menu.domain.dto.response.MenuImagesReadResponse;
import com.project.matchimban.api.menu.domain.dto.response.MenusReadResponse;
import com.project.matchimban.api.menu.repository.MenuRepositoryQuerydsl;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.matchimban.api.menu.domain.entity.QMenu.menu;
import static com.project.matchimban.api.menu.domain.entity.QMenuImage.menuImage;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Repository
@RequiredArgsConstructor
public class MenuRepositoryQuerydslImpl implements MenuRepositoryQuerydsl {
    private final JPAQueryFactory queryFactory;

    public List<MenusReadResponse> getMenusLeftJoinMenuImage(Long restaurantId) {
        return queryFactory
                .selectFrom(menu)
                .leftJoin(menuImage).on(menu.id.eq(menuImage.menu.id))
                .where(menu.restaurant.id.eq(restaurantId))
                .orderBy(menuImage.id.asc())
                .transform(groupBy(menu.id).list(Projections.constructor(MenusReadResponse.class,
                        menu,
                        list(Projections.constructor(MenuImagesReadResponse.class, menuImage.id, menuImage.savedFileUrl))
                )));
    }
}
