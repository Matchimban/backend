package com.project.matchimban.api.reservation.repository;

import com.project.matchimban.api.reservation.domain.dto.*;
import com.project.matchimban.api.reservation.domain.emums.ReservationStatus;
import com.project.matchimban.api.reservation.domain.entity.QReservation;
import com.project.matchimban.api.reservation.domain.entity.QReservationMenu;
import com.project.matchimban.api.reservation.domain.entity.QRestaurantReservation;
import com.project.matchimban.api.restaurant.domain.entity.QRestaurant;
import com.project.matchimban.api.user.domain.entity.QUser;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class ReservationRepositoryQuerydslImpl implements ReservationRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;
    QReservation reservation = QReservation.reservation;
    QRestaurantReservation restaurantReservation = QRestaurantReservation.restaurantReservation;
    QRestaurant restaurant = QRestaurant.restaurant;
    QUser user = QUser.user;
    QReservationMenu reservationMenu = QReservationMenu.reservationMenu;


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

    @Override
    public List<ReservationForUserDto> getReservationListForUser(Long userId) {
        return queryFactory
                .select(new QReservationForUserDto(reservation))
                .from(reservation)
                .join(reservation.user, user)
                .where(user.id.eq(userId)
                        .and(
                                reservation.status.stringValue().eq(ReservationStatus.SUCCESS.toString())
                                        .or(reservation.status.stringValue().eq(ReservationStatus.CANCEL.toString()))
                                        .or(reservation.status.stringValue().eq(ReservationStatus.FAIL_AND_NOT_REFUND.toString()))
                        )
                )
                .fetch();

    }

    public Long getCntOfReservation(ReservationCreatePreRequest dto){
        return queryFactory
                .select(reservation.count()) //해당 날, 시간, 좌석에 대한 개수
                .from(reservation)
                .where(reservation.rstDate.eq(dto.getRstDate())
                        .and(reservation.rstTime.eq(dto.getRstTime()))
                        .and(reservation.size.eq(dto.getSize()))
                        .and(reservation.status.in(
                                new ReservationStatus[]{
                                        ReservationStatus.ING,
                                        ReservationStatus.SUCCESS})
                        ) //ING, SUCCESS
                ).fetchOne();
    }

    @Override
    public Page<ReservationForOwnerDto> getReservationListForOwner(Pageable pageable, Long restaurantId) {
        List<ReservationForOwnerDto> content = queryFactory
                .select(new QReservationForOwnerDto(reservation))
                .from(reservation)
                    .join(reservation.user, user)
                    .join(reservation.reservationMenus, reservationMenu)
                    .join(reservation.restaurantReservation, restaurantReservation)
                .where(restaurantReservation.restaurant.id.eq(restaurantId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Long> countQuery = queryFactory
                .select(reservation.count())
                .from(reservation)
                .where(reservation.restaurantReservation.restaurant.id.eq(restaurantId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
