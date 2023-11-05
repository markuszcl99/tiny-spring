package com.tiny.spring.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author: markus
 * @date: 2023/11/5 6:45 PM
 * @Description: 自动化 Setter方法
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class ArgumentPreparedStatementSetter {
    private final Object[] args;

    public ArgumentPreparedStatementSetter(Object[] args) {
        this.args = args;
    }

    public void setValues(PreparedStatement preparedStatement) throws SQLException {
        if (this.args != null) {
            for (int i = 0; i < this.args.length; i++) {
                Object arg = this.args[i];
                doSetValue(preparedStatement, i + 1, arg);
            }
        }
    }

    /**
     * 对某个参数进行赋值
     * @param preparedStatement
     * @param parameterPosition
     * @param argValue
     * @throws SQLException
     */
    protected void doSetValue(PreparedStatement preparedStatement, int parameterPosition, Object argValue) throws SQLException {
        Object arg = argValue;
        if (arg instanceof String) {
            preparedStatement.setString(parameterPosition, (String) arg);
        } else if (arg instanceof Integer) {
            preparedStatement.setInt(parameterPosition, (Integer) arg);
        } else if (arg instanceof Long) {
            preparedStatement.setLong(parameterPosition, (Long) arg);
        }
    }
}
