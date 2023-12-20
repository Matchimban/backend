package com.project.matchimban.api.reels.domain.entity;

import com.project.matchimban.api.user.domain.entity.User;
import com.project.matchimban.common.global.TimeEntity;
import com.project.matchimban.api.reels.domain.enums.ReelsStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Table(name="reels")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// Builder 작성 필요
public class Reels extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @ColumnDefault("PUBLISHED")
    private ReelsStatus reelsStatus;
}
