package com.tiny.spring.jdbc.core;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author: markus
 * @date: 2023/11/5 2:35 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface StatementCallback {
    Object doInStatement(Statement stat) throws SQLException;
}
