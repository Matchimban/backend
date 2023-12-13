package com.project.matchimban.user.domain.entity;

import com.project.matchimban.global.TimeEntity;
import com.project.matchimban.user.domain.enums.UserRole;
import com.project.matchimban.user.domain.enums.UserStatus;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// Builder 작성 필요
public class User extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String phone;

    private String platformType;

    private String refreshToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @ColumnDefault("USER")
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @ColumnDefault("ACTIVE")
    private UserStatus status;
}
