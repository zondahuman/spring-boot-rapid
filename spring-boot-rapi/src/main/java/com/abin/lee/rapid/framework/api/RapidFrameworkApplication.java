package com.abin.lee.rapid.framework.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by abin on 2018/4/10 22:11.
 * spring-boot-rapid
 * com.abin.lee.rapid.framework.api
 */
@SpringBootApplication
//@MapperScan("com.abin.lee.rapid.framework.api.mapper")
//@MapperScan("com.abin.lee.rapid.framework.api.mapper.base")
public class RapidFrameworkApplication {

    public static void main(String[] args) {

        SpringApplication.run(RapidFrameworkApplication.class, args);

    }

}
