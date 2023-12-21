package com.project.matchimban.api.reels.domain.entity;

import com.project.matchimban.common.global.TimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name="reels_video")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// Builder 작성 필요
public class ReelsVideo extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reels_id")
    private Reels reels;

    @Column(name = "original_file_name",nullable = false)
    private String originalFileName;

    @Column(name = "saved_url", nullable = false)
    private String savedURL;
}
