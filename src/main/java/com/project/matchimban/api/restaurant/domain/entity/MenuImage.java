package com.project.matchimban.api.restaurant.domain.entity;

import com.project.matchimban.common.global.FileInfo;
import com.project.matchimban.common.global.TimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AttributeOverride(name = "updatedDate", column = @Column(insertable = false, updatable = false))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuImage extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_image_id")
    private Long id;

    @Column(nullable = false)
    private String originFileName;

    @Column(nullable = false)
    private String savedFileUrl;

    @Transient
    private LocalDateTime updatedDate;

    @OneToOne(mappedBy = "menuImage", fetch = FetchType.LAZY)
    private Menu menu;

    public static MenuImage createMenuImage(FileInfo fileInfo) {
        return MenuImage.builder()
                .originFileName(fileInfo.getOriginalFileName())
                .savedFileUrl(fileInfo.getSavedFileUrl())
                .build();
    }
}