package com.project.matchimban.common.global;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
}
