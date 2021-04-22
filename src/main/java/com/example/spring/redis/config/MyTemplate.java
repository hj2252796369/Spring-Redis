package com.example.spring.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * @program: Spring-Redis
 * @ClassName MyTemplate
 * @description:
 * @author: huJie
 * @create: 2021-04-22 21:54
 **/
@Configuration
public class MyTemplate {
    /**
     * 自定义Redis的Template
     * @param fc RedisConnectionFactory
     * @return template
     */
    @Bean
    public StringRedisTemplate customizeTemplate(RedisConnectionFactory fc) {
        StringRedisTemplate template = new StringRedisTemplate(fc);

        // 完成value string的序列化
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        return template;
    }
}
