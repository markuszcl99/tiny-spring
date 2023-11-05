package com.tiny.spring.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: markus
 * @date: 2023/11/5 9:10 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface RowMapper<T> {
    /**
     * 将结果转为一条记录
     * @param rs
     * @param rowNum
     * @return
     * @throws SQLException
     */
    T mapToRow(ResultSet rs, int rowNum) throws SQLException;
}
