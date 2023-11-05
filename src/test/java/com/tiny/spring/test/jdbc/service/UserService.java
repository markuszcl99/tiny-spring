package com.tiny.spring.test.jdbc.service;

import com.tiny.spring.beans.factory.annotation.Autowired;
import com.tiny.spring.jdbc.core.JdbcTemplate;
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

    public User getUserInfo(Long userId) {
        String sql = "select * from user where id =" + userId;
        User userByUserId = (User) jdbcTemplate.query((stat) -> {
            ResultSet resultSet = stat.executeQuery(sql);
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setAge(resultSet.getInt("age"));
                user.setName(resultSet.getString("name"));
            }
            return user;
        });
        return userByUserId;
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
