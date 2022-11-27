package com.bwie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author dxs
 * @description: 系统服务启动类
 */
@SpringBootApplication
@EnableEurekaClient
public class SystemApp {
    public static void main(String[] args) {
        SpringApplication.run(SystemApp.class);
    }
    @Bean
    public BCryptPasswordEncoder jm(){
        return new BCryptPasswordEncoder();
    }
}
