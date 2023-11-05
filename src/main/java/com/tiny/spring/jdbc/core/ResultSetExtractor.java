package com.tiny.spring.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: markus
 * @date: 2023/11/5 9:12 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface ResultSetExtractor<T> {
    /**
     * 将ResultSet映射为一个集合对象
     * @param rs
     * @return
     * @throws SQLException
     */
    T extractData(ResultSet rs) throws SQLException;
}
