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
@Table(name = "business_license")
public abstract class BusinessLicense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "license")
    private RestaurantApplication application;

    @Column(name = "tax_category")
    private TaxCategory taxCategory;

    @Column(name = "business_type")
    private String businessType;

    @Column(name = "business_category")
    private String businessCategory;

    @Column(name = "business_address")
    private String businessAddress;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "opening_day")
    private String openingDay;

    @Column(name = "origin_file_name")
    private String originFileName;

    @Column(name = "saved_file_url")
    private String savedFileUrl;
}
