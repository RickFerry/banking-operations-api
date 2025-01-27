package com.example.banking;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class BankingOperationsApiApplicationTests {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        Assertions.assertThat(applicationContext).isNotNull();
    }
}
