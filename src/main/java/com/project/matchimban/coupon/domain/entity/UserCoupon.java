package com.project.matchimban.coupon.domain.entity;

import com.project.matchimban.user.domain.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_coupon")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    private boolean active;
}
