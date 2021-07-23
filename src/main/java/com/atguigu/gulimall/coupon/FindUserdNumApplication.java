package com.atguigu.gulimall.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement //开启事务
@MapperScan(basePackages = "com.atguigu.gulimall.coupon.dao")
@SpringBootApplication
public class FindUserdNumApplication {

    public static void main(String[] args) {
        SpringApplication.run(FindUserdNumApplication.class, args);
    }

}
