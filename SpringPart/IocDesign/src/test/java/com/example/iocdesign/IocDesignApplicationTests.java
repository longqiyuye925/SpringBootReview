package com.example.iocdesign;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IocDesignApplicationTests {

    @Test
    void contextLoads() {
       assert  TestEnum.a2 instanceof TestEnum;
    }
    public enum TestEnum {
        INSTANCE1,a2;
        //编译后的字节码文件中，会变成 public static final TestEnum INSTANCE1
        //INSTANCE1 作为静态的类成员变量，是在类加载时实例化的，JVM会保证线程安全。
    }
}
