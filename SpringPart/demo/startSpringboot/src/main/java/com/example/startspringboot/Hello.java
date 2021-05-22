package com.example.startspringboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 05 2021/5/11
 */
@RestController
public class Hello {
    @RequestMapping("/hello")
    public String getString(){
        return "Hello World";
    }
}
