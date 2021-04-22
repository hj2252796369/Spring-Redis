package com.example.spring.redis;

import com.example.spring.redis.test.TestRedis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringRedisApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(SpringRedisApplication.class, args);
        TestRedis bean = ctx.getBean(TestRedis.class);

        bean.testRedis();
    }

}
