package com.project.matchimban.api.menu.repository.impl;

import com.project.matchimban.api.menu.repository.MenuRepositoryQuerydsl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MenuRepositoryQuerydslImpl implements MenuRepositoryQuerydsl {
    private final JPAQueryFactory queryFactory;

    public List<> getMenusLeftJoinMenuImage() {

    }
}
