package com.project.matchimban.api.reservation.domain.entity;


import com.project.matchimban.api.reservation.domain.emums.RestaurantReservationStatus;
import com.project.matchimban.api.restaurant.domain.Restaurant;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurant_reservation")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RestaurantReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RestaurantReservationStatus status; //상태

    @OneToMany(mappedBy = "restaurantReservation")
    private List<ReservationTime> reservationTimes;

    @OneToMany(mappedBy = "restaurantReservation")
    private List<ReservationTable> reservationTables = new ArrayList<>();

    public static RestaurantReservation createRestaurantReservation(Restaurant restaurant, RestaurantReservationStatus status){
        return RestaurantReservation.builder()
                .restaurant(restaurant)
                .status(status).build();

    }

    public void changeStatus(RestaurantReservationStatus status){
        this.status = status;
    }
}
