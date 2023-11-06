package com.tiny.spring.batis;

import com.tiny.spring.jdbc.core.JdbcTemplate;
import com.tiny.spring.jdbc.core.PreparedStatementCallback;

/**
 * @author: markus
 * @date: 2023/11/6 8:02 AM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface SqlSession {
    /**
     * 设置JDBC模板，提供动态数据源功能
     * @param jdbcTemplate
     */
    void setJdbcTemplate(JdbcTemplate jdbcTemplate);

    void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory);

    Object selectOne(String sqlId, Object[] args, PreparedStatementCallback preparedStatementCallback);
}
