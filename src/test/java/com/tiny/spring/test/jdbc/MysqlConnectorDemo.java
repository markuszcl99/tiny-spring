package com.tiny.spring.test.jdbc;

import com.tiny.spring.test.jdbc.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: markus
 * @date: 2023/11/4 12:41 PM
 * @Description: 手动建立MySQL连接 示例
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class MysqlConnectorDemo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 1. 加载数据库驱动程序
        Class<?> driver = Class.forName("com.mysql.cj.jdbc.Driver");
        // 2. 获取数据库连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/spring?useSSL=false&serverTimezone=UTC", "root", "root");
        // 3. 创建Statement对象
        String sql = "select * from user";
        Statement statement = connection.createStatement();
        // 4. 执行SQL，并获取数据，结果集为ResultSet对象
        ResultSet resultSet = statement.executeQuery(sql);
        // 5. 处理结果
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setAge(resultSet.getInt("age"));
            System.out.println(user);
        }
        // 6. 关闭数据源
        resultSet.close();
        statement.close();
        connection.close();
    }
}
