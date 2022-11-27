package com.bwie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author WuDongli
 * @version 1.0.0
 * @ClassName MsgApp.java
 * @Description TODO
 * @createTime 2022年05月27日 14:04
 */
@SpringBootApplication
@EnableEurekaClient
public class MsgApp {
    public static void main(String[] args) {
        SpringApplication.run(MsgApp.class);
    }
}
