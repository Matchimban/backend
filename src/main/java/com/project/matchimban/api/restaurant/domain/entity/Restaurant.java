package com.project.matchimban.api.restaurant.domain.entity;

import com.project.matchimban.api.restaurant.domain.enums.RestaurantCategory;
import com.project.matchimban.api.restaurant.domain.enums.RestaurantStatus;
import com.project.matchimban.api.review.domain.entity.Review;
import com.project.matchimban.api.user.domain.entity.User;
import com.project.matchimban.api.wishlist.domain.Wishlist;
import com.project.matchimban.common.global.Address;
import com.project.matchimban.common.global.TimeEntity;
import lombok.Getter;
import lombok.Setter;
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
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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

    @OneToMany(mappedBy = "restaurant")
    private List<Wishlist> wishRestaurant = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant")
    private List<Review> reviews = new ArrayList<>();
}