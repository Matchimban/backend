package com.project.matchimban.api.reservation.domain.entity;

import com.project.matchimban.api.reservation.domain.emums.RestaurantReservationStatus;
import com.project.matchimban.api.restaurant.domain.Restaurant;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "reservation_time")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReservationTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_reservation_id", nullable = false)
    private RestaurantReservation restaurantReservation;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;


    public static ReservationTime createReservationTime(RestaurantReservation restaurantReservation, LocalTime startTime, LocalTime endTime){
        return ReservationTime.builder()
                .restaurantReservation(restaurantReservation)
                .startTime(startTime)
                .endTime(endTime).build();
    }

}
