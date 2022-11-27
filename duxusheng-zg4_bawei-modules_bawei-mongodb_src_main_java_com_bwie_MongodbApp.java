package com.bwie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author dxs
 * @version 1.0.0
 * @ClassName Mongodb.java
 * @Description TODO
 * @createTime 2022年05月19日 20:56:00
 */
@SpringBootApplication
@EnableEurekaClient
@EnableScheduling
public class MongodbApp {
    public static void main(String[] args) {
        SpringApplication.run(MongodbApp.class);
    }
}
