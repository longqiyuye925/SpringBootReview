package com.example.springbootaoptest.controller;

import com.example.springbootaoptest.inteface.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 05 2021/5/14
 */
@RestController
public class AopTestController {
    @Log("执行方法一")
    @GetMapping("/one")
    public void one() {
    }

    @Log("执行方法二")
    @GetMapping("/two")
    public void two(String name) throws InterruptedException {
        Thread.sleep(2000);
    }

    @Log("执行方法三")
    @GetMapping("/three")
    public void three(String name, String sex) {

    }
}
