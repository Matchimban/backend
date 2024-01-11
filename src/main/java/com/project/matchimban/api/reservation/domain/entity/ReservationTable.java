package com.project.matchimban.api.reservation.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "reservation_table")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReservationTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_reservation_id", nullable = false)
    private RestaurantReservation restaurantReservation;

    @Column(nullable = false)
    private Integer size;//테이블 크기

    @Column(nullable = false)
    private Integer cnt;//테이블 개수


    public static ReservationTable createReservationTable(RestaurantReservation restaurantReservation, Integer size, Integer cnt){
        return ReservationTable.builder()
                .restaurantReservation(restaurantReservation)
                .size(size)
                .cnt(cnt).build();
    }
}
