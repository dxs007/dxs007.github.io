package com.bwie;

import org.bouncycastle.pqc.jcajce.provider.rainbow.BCRainbowPrivateKey;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author : dxs
 * @date : Created in 2022/10/28 15:51
 */
@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
@EnableEurekaClient
@EnableFeignClients
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class);
    }

    @Bean
    public BCryptPasswordEncoder jm(){
        return new BCryptPasswordEncoder();
    }
}
