package com.tiny.spring.batis;

/**
 * @author: markus
 * @date: 2023/11/6 8:00 AM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface SqlSessionFactory {

    SqlSession openSession();

    MapperNode getMapperNode(String name);
}
