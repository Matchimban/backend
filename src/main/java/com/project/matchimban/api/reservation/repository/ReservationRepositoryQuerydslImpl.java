package com.project.matchimban.api.reservation.repository;

import com.project.matchimban.api.reservation.domain.dto.QReservationFormDto;
import com.project.matchimban.api.reservation.domain.dto.ReservationCreateGetFormRequest;
import com.project.matchimban.api.reservation.domain.dto.ReservationFormDto;
import com.project.matchimban.api.reservation.domain.emums.ReservationStatus;
import com.project.matchimban.api.reservation.domain.entity.QReservation;
import com.project.matchimban.api.reservation.domain.entity.QRestaurantReservation;
import com.project.matchimban.api.restaurant.domain.entity.QRestaurant;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class ReservationRepositoryQuerydslImpl implements ReservationRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;
    QReservation reservation = QReservation.reservation;
    QRestaurantReservation restaurantReservation = QRestaurantReservation.restaurantReservation;
    QRestaurant restaurant = QRestaurant.restaurant;

    @Override
    public List<ReservationFormDto> getReservationListByDate(ReservationCreateGetFormRequest dto){
        return queryFactory
                .select(new QReservationFormDto(reservation.rstTime, reservation.size))
                .from(reservation)
                .join(reservation.restaurantReservation, restaurantReservation)
                .join(restaurantReservation.restaurant, restaurant)
                .where(restaurant.id.eq(dto.getRestaurantId())
                        .and(reservation.status.eq(ReservationStatus.SUCCESS))
                        .and(reservation.rstDate.eq(dto.getRstDate())))
                .fetch();
    }
}
