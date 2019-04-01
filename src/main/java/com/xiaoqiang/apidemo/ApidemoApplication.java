package com.xiaoqiang.apidemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zns.shiro.dao")
public class ApidemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApidemoApplication.class, args);
    }

}
