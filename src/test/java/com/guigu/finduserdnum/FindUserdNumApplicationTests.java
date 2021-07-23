package com.guigu.finduserdnum;

import com.atguigu.gulimall.coupon.entity.CurrentNumsEntity;
import com.atguigu.gulimall.coupon.service.CurrentNumsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FindUserdNumApplicationTests {

    @Autowired
    CurrentNumsService currentNumsService;
    @Test
    void contextLoads() {
//        List<CurrentNumsEntity> list = currentNumsService.list();
//        System.out.println(list);

    }

}
