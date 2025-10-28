package org.example.javamysqlspringboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
@RequestMapping("/api/test")
public class DatabaseTestController {

    private final DataSource dataSource;

    public DatabaseTestController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/connection")
    public String testConnection() {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT 1 as connected");

            if (resultSet.next()) {
                return "数据库连接测试成功，返回值: " + resultSet.getInt("connected");
            }
            
            return "数据库连接测试失败";
        } catch (SQLException e) {
            return "连接数据库时发生错误: " + e.getMessage();
        }
    }
}