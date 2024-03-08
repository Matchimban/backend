package com.project.matchimban.common.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Getter
@AllArgsConstructor
@RedisHash("product")
public class Product {

    @Id
    private Long id;
    private String name;
    private int price;

    public void changePrice(int price) {
        this.price = price;
    }
}