package com.tiny.spring.batis;

import com.tiny.spring.jdbc.core.JdbcTemplate;
import com.tiny.spring.jdbc.core.PreparedStatementCallback;

/**
 * @author: markus
 * @date: 2023/11/6 8:04 AM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class DefaultSqlSession implements SqlSession {
    private JdbcTemplate jdbcTemplate;
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    @Override
    public Object selectOne(String sqlId, Object[] args, PreparedStatementCallback preparedStatementCallback) {
        String sql = this.sqlSessionFactory.getMapperNode(sqlId).getSql();
        return jdbcTemplate.query(sql, args, preparedStatementCallback);
    }
}
