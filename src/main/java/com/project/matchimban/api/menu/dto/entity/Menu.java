package com.project.matchimban.api.menu.dto.entity;

import com.project.matchimban.api.menu.dto.dto.MenuUpdateRequest;
import com.project.matchimban.api.menu.dto.enums.MenuStatus;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;
import com.project.matchimban.common.global.TimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'PUBLISHED'")
    private MenuStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_image_id")
    private MenuImage menuImage;

    public static Menu createMenu(Restaurant restaurant, MenuUpdateRequest request, MenuImage menuImage) {
        return Menu.builder()
                .restaurant(restaurant)
                .name(request.getName())
                .price(request.getPrice())
                .status(MenuStatus.PUBLISHED)
                .menuImage(menuImage)
                .build();
    }
}
