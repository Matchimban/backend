package com.project.matchimban;

import com.project.matchimban.common.test.Product;
import com.project.matchimban.common.test.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ProductTests2 {

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product(1L, "테스트_상품", 20000);
        productRepository.save(product);
    }

    @AfterEach
    public void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    public void saveRedisTest() {
        Product persistProduct = productRepository.findById(product.getId())
                .orElseThrow(RuntimeException::new);

        assertThat(persistProduct.getId()).isEqualTo(product.getId());
        assertThat(persistProduct.getName()).isEqualTo(product.getName());
        assertThat(persistProduct.getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    public void redis_update_test() {
        Product persistProduct = productRepository.findById(product.getId())
                .orElseThrow(RuntimeException::new);

        // when
        persistProduct.changePrice(35000);
        productRepository.save(persistProduct);

        // then
        assertThat(persistProduct.getPrice()).isEqualTo(35000);
    }

    @Test
    public void redis_delete_test() {
        // when
        productRepository.delete(product);
        Optional<Product> deletedProduct = productRepository.findById(product.getId());

        // then
        assertTrue(deletedProduct.isEmpty());
    }
}
