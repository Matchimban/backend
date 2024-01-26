package com.project.matchimban.api.review.domain.entity;

import com.project.matchimban.api.review.domain.enums.ReviewStatus;
import com.project.matchimban.common.global.TimeEntity;
import com.project.matchimban.api.reservation.domain.entity.Reservation;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;
import com.project.matchimban.api.user.domain.entity.User;
import lombok.Getter;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AttributeOverride(name = "updatedDate", column = @Column(insertable = false, updatable = false))
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

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private double rating;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'PUBLISHED'")
    private ReviewStatus status;

    @OneToOne(mappedBy = "review")
    private Reply Reply;

    @OneToMany(mappedBy = "review")
    private List<ReviewImage> reviewImages = new ArrayList<>();
}
