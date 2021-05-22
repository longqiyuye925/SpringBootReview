package com.example.aopdesign;

import com.example.aopdesign.core.AspectWeaver;
import com.example.aopdesign.core.BeanContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AopDesignApplicationTests {

    @Test
    void contextLoads() {
        BeanContainer.getInstance().addBean(TestOrdinaryClass.class, new TestOrdinaryClass());
        BeanContainer.getInstance().addBean(TestAspect.class, new TestAspect());

        AspectWeaver aspectWeaver = new AspectWeaver();
        aspectWeaver.doAop();
        TestOrdinaryClass testOrdinaryClass = (TestOrdinaryClass) BeanContainer.getInstance().getBean(TestOrdinaryClass.class);
        System.out.println(testOrdinaryClass.getClass());
        testOrdinaryClass.test();
    }

}
