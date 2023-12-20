package com.project.matchimban;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PublicUnitTestResult {

    @Test
    public void test1() {
        // given
        // when
        // then
        assertThat(1).isEqualTo(2);
    }

    @Test
    public void test2() throws Exception {
        // given
        // when
        // then
        assertThat(1).isEqualTo(2);
    }
}
