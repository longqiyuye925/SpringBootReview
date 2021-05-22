package com.example.springbootaoptest;

import com.example.springbootaoptest.controller.AopTestController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootAopTestApplicationTests {

    @Test
    void contextLoads() {
        AopTestController aopTestController = new AopTestController();
        aopTestController.one();
    }

}
