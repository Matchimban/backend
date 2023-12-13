package com.project.matchimban.reservation.entity;

import com.project.matchimban.global.TimeEntity;
import com.project.matchimban.review.domain.Review;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
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

    //매장_예약 fk
    //예악자 fk
    //회원_쿠폰 fk

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
