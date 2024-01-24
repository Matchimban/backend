package com.project.matchimban.api.menu.domain;

import com.project.matchimban.common.global.TimeEntity;
import lombok.Getter;

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
@AttributeOverride(name = "updatedDate", column = @Column(insertable = false, updatable = false))
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
}