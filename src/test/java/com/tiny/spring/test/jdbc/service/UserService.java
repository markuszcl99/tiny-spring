package com.tiny.spring.test.jdbc.service;

import com.tiny.spring.batis.SqlSession;
import com.tiny.spring.batis.SqlSessionFactory;
import com.tiny.spring.beans.factory.annotation.Autowired;
import com.tiny.spring.jdbc.core.JdbcTemplate;
import com.tiny.spring.jdbc.core.RowMapper;
import com.tiny.spring.jdbc.core.StatementCallback;
import com.tiny.spring.test.jdbc.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: markus
 * @date: 2023/11/5 2:39 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public User getUserInfo(Long userId) {
        String sqlId = "com.tiny.spring.test.jdbc.entity.User.getUserInfo";
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return (User) sqlSession.selectOne(sqlId, new Object[]{userId}, (pstmt) -> {
            ResultSet rs = pstmt.executeQuery();
            User user = null;
            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setAge(rs.getInt("age"));
            }
            return user;
        });
    }

    public User getUserInfoByPStat(Long userId) {
        String sql = "select * from user where id=?";
        User userByUserId = (User) jdbcTemplate.query(sql, new Object[]{userId}, (preparedStatement) -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setAge(resultSet.getInt("age"));
                user.setName(resultSet.getString("name"));
                return user;
            }
            return null;
        });
        return userByUserId;
    }

    public List<User> getUserInfoListByNotSpecifyId(Long userId) {
        String sql = "select * from user where id != ?";
        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapToRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setAge(age);
                return user;
            }
        };
        return jdbcTemplate.query(sql, new Object[]{userId}, rowMapper);
    }

//    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    public static void main(String[] args) {
        UserService userService = new UserService();
        User userInfo = userService.getUserInfo(1L);
        System.out.println(userInfo);

        userInfo = userService.getUserInfo(2L);
        System.out.println(userInfo);

    }
}
