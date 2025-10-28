import com.mysql.cj.log.LogFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) {
        // 日志工厂
        System.out.println("正在连接到 MySQL 数据库...");

        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("成功连接到数据库！");

            // 测试查询
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT 1 as connected");

            if (resultSet.next()) {
                System.out.println("数据库连接测试成功，返回值: " + resultSet.getInt("connected"));
            }

            // 关闭资源
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.err.println("连接数据库时发生错误: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 关闭数据源
            DatabaseConnection.closeDataSource();
        }
    }
}