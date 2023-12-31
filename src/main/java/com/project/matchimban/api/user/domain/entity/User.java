package com.project.matchimban.api.user.domain.entity;

import com.project.matchimban.api.restaurant_application.domain.RestaurantApplication;
import com.project.matchimban.api.review.domain.Review;
import com.project.matchimban.common.global.TimeEntity;
import com.project.matchimban.api.restaurant.domain.Restaurant;
import com.project.matchimban.api.user.domain.enums.UserRole;
import com.project.matchimban.api.user.domain.enums.UserStatus;
import com.project.matchimban.api.wishlist.domain.Wishlist;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// Builder 작성 필요
public class User extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String phone;

    private String platformType;

    private String refreshToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @ColumnDefault("USER")
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @ColumnDefault("ACTIVE")
    private UserStatus status;

    @OneToMany(mappedBy = "user")
    private List<Restaurant> restaurants = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Wishlist> wishlists = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<RestaurantApplication> applications = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();
}
