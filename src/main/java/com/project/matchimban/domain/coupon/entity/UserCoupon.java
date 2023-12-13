package com.project.matchimban.domain.coupon.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //유저 fk
    //쿠폰 fk

    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    private boolean active;
}
