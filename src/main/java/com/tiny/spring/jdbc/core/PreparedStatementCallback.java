package com.tiny.spring.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author: markus
 * @date: 2023/11/5 5:36 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface PreparedStatementCallback {
    Object doInStatement(PreparedStatement preparedStatement) throws SQLException;
}
