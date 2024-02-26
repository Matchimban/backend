package com.project.matchimban.api.reservation.repository;

import com.project.matchimban.api.reservation.domain.entity.QReservationSeat;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationSeatRepositoryQuerydslImpl implements ReservationSeatRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;
    QReservationSeat reservationSeat = QReservationSeat.reservationSeat;

    @Override
    public void deleteByRestaurantReservationId(Long restaurantReservationId) {
        queryFactory
                .delete(reservationSeat)
                .where(reservationSeat.restaurantReservation.id.eq(restaurantReservationId))
                .execute();
        return;
    }
}
