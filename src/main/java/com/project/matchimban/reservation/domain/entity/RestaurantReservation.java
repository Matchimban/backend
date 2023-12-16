package com.project.matchimban.reservation.domain.entity;


import com.project.matchimban.restaurant.domain.Restaurant;
import lombok.*;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
}
