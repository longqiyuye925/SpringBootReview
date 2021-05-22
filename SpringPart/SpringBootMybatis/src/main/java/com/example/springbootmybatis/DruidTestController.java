package com.example.springbootmybatis;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 05 2021/5/12
 */
@RestController
public class DruidTestController {
    @RequestMapping("/helloDruid")
    public String helloDruid() {
        return "helloDruid";
    }
}
