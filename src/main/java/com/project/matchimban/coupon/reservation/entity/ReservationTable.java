package com.project.matchimban.coupon.reservation.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReservationTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //매장_예약 fk


    @Column(nullable = false)
    private Integer size;//테이블 크기

    @Column(nullable = false)
    private Integer cnt;//테이블 개수
}
