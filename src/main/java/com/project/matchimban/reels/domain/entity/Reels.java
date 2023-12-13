package com.project.matchimban.reels.domain.entity;

import com.project.matchimban.global.TimeEntity;
import com.project.matchimban.reels.domain.enums.ReelsStatus;
import com.project.matchimban.user.domain.entity.User;
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
