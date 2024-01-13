package com.project.matchimban.api.review.domain.entity;

import com.project.matchimban.common.global.TimeEntity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
public class ReviewImage extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(nullable = false)
    private String originFileName;

    @Column(nullable = false)
    private String savedFileName;
}
