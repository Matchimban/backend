package com.project.matchimban.api.restaurant.domain.entity;

import com.project.matchimban.api.reservation.domain.entity.RestaurantReservation;
import com.project.matchimban.api.restaurant.domain.dto.request.RestaurantCreateRequest;
import com.project.matchimban.api.restaurant.domain.dto.request.RestaurantUpdateRequest;
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
import javax.persistence.FetchType;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
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

    @Builder.Default
    @OneToMany(mappedBy = "restaurant")
    private List<Wishlist> wishRestaurant = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "restaurant")
    private List<Review> reviews = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "restaurant")
    private List<RestaurantImage> restaurantImages = new ArrayList<>();

    public static Restaurant createRestaurant(RestaurantCreateRequest request, User user, Address address) {
        return Restaurant.builder()
                .category(request.getCategory())
                .name(request.getName())
                .businessNumber(request.getBusinessNumber())
                .originCountry(request.getOriginCountry())
                .introduction(request.getIntroduction())
                .telephone(request.getTelephone())
                .businessHours(request.getBusinessHours())
                .closedDays(request.getClosedDays())
                .notice(request.getNotice())
                .status(RestaurantStatus.UNAUTHORIZED)
                .user(user)
                .address(address)
                .build();
    }

    public void updateRestaurant(RestaurantUpdateRequest request, Address address) {
        if (request.getBusinessHours() != null)
            this.businessHours = request.getBusinessHours();
        if (request.getCategory() != null)
            this.category = request.getCategory();
        if (request.getNotice() != null)
            this.notice = request.getNotice();
        if (request.getName() != null)
            this.name = request.getName();
        if (request.getTelephone() != null)
            this.telephone = request.getTelephone();
        if (request.getIntroduction() != null)
            this.introduction = request.getIntroduction();
        if (request.getBusinessHours() != null)
            this.businessHours = request.getBusinessHours();
        if (address != null)
            this.address = address;
        if (request.getClosedDays() != null)
            this.closedDays = request.getClosedDays();
        if (request.getStatus() != null)
            this.status = request.getStatus();
    }
}