package com.bwie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 * @author dxs
 * @version 1.0.0
 * @ClassName EsApp.java
 * @Description TODO
 */
@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
@EnableEurekaClient
@EnableFeignClients
public class EsApp {
    public static void main(String[] args) {
        SpringApplication.run(EsApp.class);
    }
}
