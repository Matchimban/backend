package com.project.matchimban.restaurant.domain;

import com.project.matchimban.User;
import com.project.matchimban.global.Address;
import com.project.matchimban.global.TimeEntity;
import com.project.matchimban.review.domain.Review;
import com.project.matchimban.wishlist.domain.Wishlist;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
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
@Table(name = "restaurant")
public class Restaurant extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private RestaurantCategory category;

    private String name;

    @Column(name = "business_number")
    private Long businessNumber;

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

    private String deleted;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;
}
