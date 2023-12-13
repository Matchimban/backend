package com.project.matchimban.follow.domain.entity;

import com.project.matchimban.global.TimeEntity;
import com.project.matchimban.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name="follow",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_senderId_receiverId",
                        columnNames = {"sender_id", "receiver_id"}
                )
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;
}
