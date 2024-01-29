package com.project.matchimban.api.reservation.repository;

import com.project.matchimban.api.reservation.domain.entity.QReservationTime;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationTimeQuerydslRepositoryImpl implements ReservationTimeRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    QReservationTime reservationTime = QReservationTime.reservationTime;

    @Override
    public void deleteByRestaurantReservationId(Long restaurantReservationId){
        queryFactory
                .delete(reservationTime)
                .where(reservationTime.restaurantReservation.id.eq(restaurantReservationId))
                .execute();
        return;
    }
}
