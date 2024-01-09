package com.project.matchimban.common.global;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {
    @Column(nullable = false)
    private String addrSido;
    @Column(nullable = false)
    private String addrSigg;
    @Column(nullable = false)
    private String addrEmd;
    @Column(nullable = false)
    private String addrDetail;

    @Column(nullable = false)
    private double latitude;
    @Column(nullable = false)
    private double longitude;

    protected Address() {
    }

    public Address(String addrSido, String addrSigg, String addrEmd, String addrDetail, double latitude, double longitude) {
        this.addrSido = addrSido;
        this.addrSigg = addrSigg;
        this.addrEmd = addrEmd;
        this.addrDetail = addrDetail;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
