package com.project.matchimban.api.reservation.domain.entity;

import com.project.matchimban.api.coupon.domain.entity.UserCoupon;
import com.project.matchimban.api.review.domain.entity.Review;
import com.project.matchimban.api.user.domain.entity.User;
import com.project.matchimban.common.global.TimeEntity;
import com.project.matchimban.api.reservation.domain.emums.ReservationStatus;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "reservation")
@DynamicInsert
@DynamicUpdate
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Reservation extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_reservation_id", nullable = false)
    private RestaurantReservation restaurantReservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_coupon_id")
    private UserCoupon userCoupon;

    @Column(nullable = false)
    private Integer size;//선택테이블 크기

    @Column(nullable = false)
    private LocalDateTime startDate; //시작일

    @Column(nullable = false)
    private LocalDateTime endDate; //종료일

    private LocalDateTime cancelDate; //취소일

    @Column(nullable = false)
    private LocalDateTime reviewDate; //리뷰 마감 시간

    @Column(nullable = false)
    private Integer regularPrice; //정가
    @Column(nullable = false)
    private Integer paymentAmount; //결제가
    @ColumnDefault("0")
    private Integer refundAmount; //환불금

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status; //상태
    private String failReason; //실패이유

    @OneToOne(mappedBy = "reservation")
    private Review review;
}
