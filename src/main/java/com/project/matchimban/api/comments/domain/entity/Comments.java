package com.project.matchimban.api.comments.domain.entity;

import com.project.matchimban.api.comments.domain.enums.CommentsStatus;
import com.project.matchimban.common.global.TimeEntity;
import com.project.matchimban.api.reels.domain.entity.Reels;
import com.project.matchimban.api.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Table(name="comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// Builder 작성 필요
public class Comments extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comments parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reels_id")
    private Reels reels;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @ColumnDefault("'PUBLISHED'")
    private CommentsStatus status;
}
