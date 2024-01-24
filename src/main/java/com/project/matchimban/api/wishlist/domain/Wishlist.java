package com.project.matchimban.api.wishlist.domain;

import com.project.matchimban.api.user.domain.entity.User;
import com.project.matchimban.common.global.TimeEntity;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@AttributeOverride(name = "updatedDate", column = @Column(insertable = false, updatable = false))
public class Wishlist extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
