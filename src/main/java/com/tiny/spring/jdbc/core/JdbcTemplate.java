package com.tiny.spring.jdbc.core;

import com.tiny.spring.beans.factory.annotation.Autowired;
import org.apache.jasper.tagplugins.jstl.core.If;

import javax.sql.DataSource;
import java.sql.*;

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

    public Object query(String sql, Object[] args, PreparedStatementCallback preparedStatementCallback) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                Object arg = args[0];
                if (arg instanceof String) {
                    preparedStatement.setString(i + 1, (String) arg);
                } else if (arg instanceof Integer) {
                    preparedStatement.setInt(i + 1, (Integer) arg);
                } else if (arg instanceof Long) {
                    preparedStatement.setLong(i + 1, (Long) arg);
                }
            }
            return preparedStatementCallback.doInStatement(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public void setDataSource(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
}
