package com.project.matchimban.api.reservation.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "reservation_menu")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReservationMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;


    private String menuName; //메뉴 이름

    private Integer regularPrice;//정가

    private Integer paymentAmount;//결제가
}
