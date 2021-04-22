package com.example.spring.redis.test;

import com.example.spring.redis.config.MyTemplate;
import com.example.spring.redis.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;

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
    @Qualifier("customizeTemplate")
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MyTemplate myTemplate;

    /**
     * 测试
     *
     * @throws JsonProcessingException
     */
    public void testRedis() throws JsonProcessingException {
        redisTemplate.opsForValue().set("hello", "Hello , SpringBoot Redis");

        System.out.println(redisTemplate.opsForValue().get("hello"));

        stringRedisTemplate.opsForValue().set("stringRedisTemplate", "stringRedisTemplate。。。。。");
        System.out.println(stringRedisTemplate.opsForValue().get("stringRedisTemplate"));

        RedisConnection conn = redisTemplate.getConnectionFactory().getConnection();
        conn.set("conn".getBytes(StandardCharsets.UTF_8), "Connection".getBytes(StandardCharsets.UTF_8));
        System.out.println(new String(conn.get("conn".getBytes(StandardCharsets.UTF_8))));


        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        // hash.put("sean", "name", "zhangsan");
        // hash.put("sean", "age", "22");
        // System.out.println(hash.entries("sean"));


        Person person = new Person("zhangsan", "56");

        // 完成value string的序列化
//        stringRedisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));

        Jackson2HashMapper jackson2HashMapper = new Jackson2HashMapper(objectMapper, false);

        stringRedisTemplate.opsForHash().putAll("zhangsan", jackson2HashMapper.toHash(person));

        Map<Object, Object> zhangsanMap = stringRedisTemplate.opsForHash().entries("zhangsan");
        Person zhangsan = objectMapper.convertValue(zhangsanMap, Person.class);
        System.out.println(zhangsan);



        // 发布订阅   在redis-cli中模拟发布消息：  PUBLISH channelOne hee
        stringRedisTemplate.convertAndSend("channelOne", "hello Channel");

        RedisConnection connection = stringRedisTemplate.getConnectionFactory().getConnection();
        connection.subscribe(new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] bytes) {
                byte[] body = message.getBody();
                System.out.println(new String(body));
            }
        }, "channelOne".getBytes(StandardCharsets.UTF_8));

        while (true) {
            // 发布订阅   在redis-cli中模拟发布消息：  PUBLISH channelOne hee
            stringRedisTemplate.convertAndSend("channelOne", "hello Channel");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
