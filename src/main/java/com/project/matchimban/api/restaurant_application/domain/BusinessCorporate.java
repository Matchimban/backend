package com.project.matchimban.api.restaurant_application.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "business_corporate")
public class BusinessCorporate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "license_id")
    private BusinessLicense license;

    @Column(name = "corporate_name")
    private String corporateName;

    @Column(name = "representative_name")
    private String representativeName;

    @Column(name = "corporate_registration_number")
    private String corporateRegistrationNumber;

    @Column(name = "hq_address")
    private String hqAddress;
}
