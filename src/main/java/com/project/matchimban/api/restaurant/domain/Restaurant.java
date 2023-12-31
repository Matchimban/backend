package com.project.matchimban.api.restaurant.domain;

import com.project.matchimban.api.review.domain.Review;
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
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "restaurant")
public class Restaurant extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("KOREA")
    private RestaurantCategory category;

    @Column(nullable = false)
    private String name;

    @Column(name = "business_number", nullable = false)
    private String businessNumber;

    private String introduction;
    private String telephone;

    @Column(name = "business_hours")
    private String businessHours;

    @Column(name = "closed_days")
    private String closedDays;

    @Embedded
    private Address address;
    private String notice;

    @Column(name = "origin_country")
    private String originCountry;

    @OneToMany(mappedBy = "restaurant")
    private List<Wishlist> wishRestaurant = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant")
    private List<Review> reviews = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("PUBLISHED")
    private RestaurantStatus status;
}
