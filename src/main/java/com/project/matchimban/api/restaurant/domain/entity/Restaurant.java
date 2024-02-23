package com.project.matchimban.api.restaurant.domain.entity;

import com.project.matchimban.api.reservation.domain.entity.RestaurantReservation;
import com.project.matchimban.api.restaurant.domain.dto.RestaurantCreateDTO;
import com.project.matchimban.api.restaurant.domain.enums.RestaurantCategory;
import com.project.matchimban.api.restaurant.domain.enums.RestaurantStatus;
import com.project.matchimban.api.review.domain.entity.Review;
import com.project.matchimban.api.user.domain.entity.User;
import com.project.matchimban.api.wishlist.domain.Wishlist;
import com.project.matchimban.common.global.Address;
import com.project.matchimban.common.global.TimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'KOREA'")
    private RestaurantCategory category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String businessNumber;

    private String introduction;
    private String telephone;
    private String businessHours;
    private String closedDays;
    private String notice;

    @Embedded
    @Column(nullable = false)
    private Address address;

    @Column(nullable = false)
    private String originCountry;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'PUBLISHED'")
    private RestaurantStatus status;

    @OneToOne(mappedBy = "restaurant")
    private RestaurantReservation restaurantReservation;

    @OneToMany(mappedBy = "restaurant")
    private List<Wishlist> wishRestaurant = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant")
    private List<Review> reviews = new ArrayList<>();

    public static Restaurant createRestaurant(RestaurantCreateDTO dto, User user, Address address) {
        return Restaurant.builder()
                .category(dto.getCategory())
                .name(dto.getName())
                .businessNumber(dto.getBusinessNumber())
                .originCountry(dto.getOriginCountry())
                .introduction(dto.getIntroduction())
                .telephone(dto.getTelephone())
                .businessHours(dto.getBusinessHours())
                .closedDays(dto.getClosedDays())
                .notice(dto.getNotice())
                .status(RestaurantStatus.UNAUTHORIZED)
                .user(user)
                .address(address)
                .build();
    }
}