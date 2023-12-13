package com.project.matchimban.restaurant_application.domain;

import com.project.matchimban.global.TimeEntity;
import com.project.matchimban.user.domain.entity.User;
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
@Table(name = "restaurant_application")
public class RestaurantApplication extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "license_id")
    private BusinessLicense license;

    @OneToOne
    @JoinColumn(name = "report_id")
    private BusinessReport report;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private RestaurantApplicationStatus status;
}
