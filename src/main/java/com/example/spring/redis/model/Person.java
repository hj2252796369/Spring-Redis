package com.example.spring.redis.model;

import java.io.Serializable;

/**
 * @program: Spring-Redis
 * @ClassName Person
 * @description:
 * @author: huJie
 * @create: 2021-04-22 21:28
 **/
public class Person implements Serializable {
    private String name;
    private String age;

    public Person() {
    }

    public Person(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
