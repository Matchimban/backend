package com.project.matchimban.restaurant_application.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "business_report")
public class BusinessReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "report")
    private RestaurantApplication application;

    @Column(name = "license_number")
    private String licenseNumber;

    @Column(name = "representative_name")
    private String representativeName;

    @Column(name = "trade_name")
    private String tradeName;

    private String address;

    @Column(name = "business_category")
    private String businessCategory;

    @Column(name = "origin_file_name")
    private String originFileName;

    @Column(name = "saved_file_url")
    private String savedFileUrl;
}
