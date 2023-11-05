package com.tiny.spring.jdbc.core;

import com.sun.istack.internal.Nullable;
import com.tiny.spring.beans.factory.annotation.Autowired;
import org.apache.jasper.tagplugins.jstl.core.If;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * @author: markus
 * @date: 2023/11/5 2:22 PM
 * @Description: Jdbc连接模板
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class JdbcTemplate {

    @Autowired
    private DataSource dataSource;

    public JdbcTemplate() {

    }

    /**
     * 无参数输入
     *
     * @param statementCallback
     * @return
     */
    public Object query(StatementCallback statementCallback) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();

            return statementCallback.doInStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 有参数输入
     *
     * @param sql
     * @param args
     * @param preparedStatementCallback
     * @return
     */
    public Object query(String sql, Object[] args, PreparedStatementCallback preparedStatementCallback) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            ArgumentPreparedStatementSetter argumentSetter = new ArgumentPreparedStatementSetter(args);
            argumentSetter.setValues(preparedStatement);
            return preparedStatementCallback.doInStatement(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 抽象集合数据映射，将映射的逻辑从回调函数转为用户自定义rowMapper
     *
     * @param sql
     * @param args
     * @param rowMapper
     * @param <T>
     * @return
     */
    @Nullable
    public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) {
        RowMapperResultSetExtractor<T> rowMapperResultSetExtractor = new RowMapperResultSetExtractor<T>(rowMapper);

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // 获取连接
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);

            // 准备参数
            ArgumentPreparedStatementSetter argumentSetter = new ArgumentPreparedStatementSetter(args);
            argumentSetter.setValues(statement);

            // 执行SQL
            ResultSet resultSet = statement.executeQuery();

            // 记录映射
            return rowMapperResultSetExtractor.extractData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
