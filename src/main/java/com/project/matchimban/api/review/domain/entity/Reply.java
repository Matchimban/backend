package com.project.matchimban.api.review.domain.entity;

import com.project.matchimban.api.review.domain.enums.ReplyStatus;
import com.project.matchimban.common.global.TimeEntity;
import com.project.matchimban.api.user.domain.entity.User;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Getter
@AttributeOverride(name = "updatedDate", column = @Column(insertable = false, updatable = false))
public class Reply extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(nullable = false)
    private String content;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'PUBLISHED'")
    private ReplyStatus status;
}
