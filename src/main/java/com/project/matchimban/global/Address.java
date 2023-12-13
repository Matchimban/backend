package com.project.matchimban.global;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {
    private String sido;
    private String sigg;
    private String emd;
    private String detail;

    @Column(name = "lat")
    private double latitude;
    @Column(name = "lon")
    private double longitude;

    protected Address() {
    }

    public Address(String sido, String sigg, String emd, String detail, double latitude, double longitude) {
        this.sido = sido;
        this.sigg = sigg;
        this.emd = emd;
        this.detail = detail;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
