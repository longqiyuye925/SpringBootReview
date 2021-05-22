package com.example.basicconfigration;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BasicConfigrationApplication {

    public static void main(String[] args) {

        // SpringApplication.run(BasicConfigrationApplication.class, args);
        //关闭启动图
        SpringApplication springApplication = new SpringApplication(BasicConfigrationApplication.class);
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.run(args);
    }

}
