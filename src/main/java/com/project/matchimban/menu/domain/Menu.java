package com.project.matchimban.menu.domain;

import com.project.matchimban.global.TimeEntity;
import com.project.matchimban.restaurant.domain.Restaurant;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "menu")
public class Menu extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private String name;
    private int price;

    private MenuStatus status;

    @OneToOne(mappedBy = "menu")
    private MenuImage menuImage;
}
