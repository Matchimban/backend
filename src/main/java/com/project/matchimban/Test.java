package com.project.matchimban;

import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter
public class Test {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
