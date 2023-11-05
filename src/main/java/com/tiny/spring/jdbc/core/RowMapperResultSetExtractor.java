package com.tiny.spring.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: markus
 * @date: 2023/11/5 9:13 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class RowMapperResultSetExtractor<T> implements ResultSetExtractor<List<T>> {

    private final RowMapper<T> rowMapper;

    public RowMapperResultSetExtractor(RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
    }

    @Override
    public List<T> extractData(ResultSet rs) throws SQLException {
        List<T> result = new ArrayList<>(rs.getFetchSize());
        int rowNum = 0;
        // 对结果集，循环执行映射动作，并将映射的结果添加到集合中
        while (rs.next()) {
            result.add(rowMapper.mapToRow(rs, rowNum++));
        }
        return result;
    }
}
