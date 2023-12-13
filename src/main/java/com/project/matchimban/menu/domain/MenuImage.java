package com.project.matchimban.menu.domain;

import com.project.matchimban.global.TimeEntity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "menu_image")
public class MenuImage extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(name = "origin_file_name")
    private String originFileName;

    @Column(name = "saved_file_name")
    private String savedFileName;
}
