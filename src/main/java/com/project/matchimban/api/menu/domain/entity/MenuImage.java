package com.project.matchimban.api.menu.domain.entity;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Builder
@AttributeOverride(name = "updatedDate", column = @Column(insertable = false, updatable = false))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuImage extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originFileName;

    @Column(nullable = false)
    private String savedFileUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, name = "menu_id")
    private Menu menu;

    public static MenuImage createMenuImage(FileInfo fileInfo, Menu menu) {
        return MenuImage.builder()
                .originFileName(fileInfo.getOriginalFileName())
                .savedFileUrl(fileInfo.getSavedFileUrl())
                .menu(menu)
                .build();
    }
}