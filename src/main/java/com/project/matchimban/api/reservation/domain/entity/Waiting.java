package com.project.matchimban.api.reservation.domain.entity;

import com.project.matchimban.api.restaurant.domain.entity.Restaurant;
import com.project.matchimban.api.user.domain.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "waiting")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Waiting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
