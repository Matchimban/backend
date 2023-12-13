package com.project.matchimban.restaurant.domain;

import com.project.matchimban.global.TimeEntity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "restaurant_image")
public class RestaurantImage extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "origin_file_name")
    private String originFileName;

    @Column(name = "saved_file_name")
    private String savedFileName;

    @Column(name = "image_category")
    private ImageCategory imageCategory;
}
