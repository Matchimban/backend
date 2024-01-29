package com.project.matchimban.api.reservation.repository;

import com.project.matchimban.api.reservation.domain.dto.ReservationCreateGetFormRequest;
import com.project.matchimban.api.reservation.domain.emums.ReservationStatus;
import com.project.matchimban.api.reservation.domain.entity.QReservation;
import com.project.matchimban.api.reservation.domain.entity.QRestaurantReservation;
import com.project.matchimban.api.reservation.domain.entity.Reservation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class ReservationQueryRepository {

    private final JPAQueryFactory queryFactory;
    QReservation reservation = QReservation.reservation;
    QRestaurantReservation restaurantReservation = QRestaurantReservation.restaurantReservation;

    public List<Reservation> getReservationListByDate(ReservationCreateGetFormRequest dto){
        return queryFactory
                .selectFrom(reservation)
                .join(reservation.restaurantReservation, restaurantReservation).fetchJoin()
                .where(restaurantReservation.restaurant.id.eq(dto.getRestaurantId())
                        .and(reservation.status.eq(ReservationStatus.SUCCESS))
                        .and(reservation.rstDate.eq(dto.getRstDate())))
                .fetch();
    }

}
