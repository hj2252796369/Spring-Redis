package com.example.spring.redis.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @program: Spring-Redis
 * @ClassName TestRedis
 * @description:
 * @author: huJie
 * @create: 2021-04-22 20:59
 **/
@Component
public class TestRedis {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public void testRedis(){
        redisTemplate.opsForValue().set("hello", "Hello , SpringBoot Redis");

        System.out.println(redisTemplate.opsForValue().get("hello"));

        stringRedisTemplate.opsForValue().set("stringRedisTemplate", "stringRedisTemplate。。。。。");
        System.out.println(stringRedisTemplate.opsForValue().get("stringRedisTemplate"));


        RedisConnection conn = redisTemplate.getConnectionFactory().getConnection();
        conn.set("conn".getBytes(StandardCharsets.UTF_8), "Connection".getBytes(StandardCharsets.UTF_8));
        System.out.println(new String(conn.get("conn".getBytes(StandardCharsets.UTF_8))));

    }
}
