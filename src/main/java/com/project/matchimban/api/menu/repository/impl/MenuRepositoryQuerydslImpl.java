package com.project.matchimban.api.menu.repository.impl;

import com.project.matchimban.api.menu.dto.dto.response.MenusReadResponse;
import com.project.matchimban.api.menu.dto.dto.response.QMenusReadResponse;
import com.project.matchimban.api.menu.repository.MenuRepositoryQuerydsl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.matchimban.api.menu.dto.entity.QMenu.menu;
import static com.project.matchimban.api.menu.dto.entity.QMenuImage.menuImage;

@Repository
@RequiredArgsConstructor
public class MenuRepositoryQuerydslImpl implements MenuRepositoryQuerydsl {
    private final JPAQueryFactory queryFactory;

    public List<MenusReadResponse> getMenusLeftJoinMenuImage() {
        return queryFactory
                .select(new QMenusReadResponse(menu, menuImage.savedFileUrl))
                .from(menu)
                .leftJoin(menu.images, menuImage)
                .fetch();
    }
}
