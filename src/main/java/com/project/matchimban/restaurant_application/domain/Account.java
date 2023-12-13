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
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "account")
    private RestaurantApplication application;

    private String bank;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "origin_file_name")
    private String originFileName;

    @Column(name = "saved_file_name")
    private String savedFileName;
}
