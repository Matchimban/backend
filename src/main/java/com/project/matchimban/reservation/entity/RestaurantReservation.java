package com.project.matchimban.reservation.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RestaurantReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //메장 fk 설정
}
