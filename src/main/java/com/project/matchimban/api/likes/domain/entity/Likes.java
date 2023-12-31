package com.project.matchimban.api.likes.domain.entity;

import com.project.matchimban.common.global.TimeEntity;
import com.project.matchimban.api.reels.domain.entity.Reels;
import com.project.matchimban.api.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name="likes",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uq_reelsId_userId",
                    columnNames = {"reels_id", "user_id"}
            )
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reels_id")
    private Reels reels;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
