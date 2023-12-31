package com.project.matchimban.api.review.domain;

import com.project.matchimban.common.global.TimeEntity;
import com.project.matchimban.api.reservation.domain.entity.Reservation;
import com.project.matchimban.api.restaurant.domain.Restaurant;
import com.project.matchimban.api.user.domain.entity.User;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "review")
public class Review extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private String content;

    private double rating;

    @OneToOne(mappedBy = "review")
    private Reply Reply;

    @OneToMany(mappedBy = "review")
    private List<ReviewImage> reviewImages = new ArrayList<>();

    private ReviewStatus status;
}
