package com.project.matchimban.api.restaurant.domain.entity;

import com.project.matchimban.api.restaurant.domain.enums.RestaurantImageCategory;
import com.project.matchimban.common.global.FileInfo;
import com.project.matchimban.common.global.TimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Builder
@AttributeOverride(name = "updatedDate", column = @Column(insertable = false, updatable = false))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantImage extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private String originFileName;

    private String savedFileUrl;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'MAIN'")
    private RestaurantImageCategory imageCategory;

    public static RestaurantImage createMainRestaurantImage(Restaurant restaurant, FileInfo fileInfo) {
        return RestaurantImage.builder()
                .restaurant(restaurant)
                .originFileName(fileInfo.getOriginalFileName())
                .savedFileUrl(fileInfo.getSavedFileUrl())
                .imageCategory(RestaurantImageCategory.MAIN)
                .build();
    }

    public static RestaurantImage createSubRestaurantImage(Restaurant restaurant, FileInfo fileInfo) {
        return RestaurantImage.builder()
                .restaurant(restaurant)
                .originFileName(fileInfo.getOriginalFileName())
                .savedFileUrl(fileInfo.getSavedFileUrl())
                .imageCategory(RestaurantImageCategory.SUB)
                .build();
    }
}
