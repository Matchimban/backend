package com.project.matchimban.common.global;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {
    private String addrSido;
    private String addrSigg;
    private String addrEmd;
    private String addrDetail;

    @Column(name = "lat")
    private double latitude;
    @Column(name = "lon")
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
