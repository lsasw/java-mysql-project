import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariCPDataSource {
    private static HikariDataSource dataSource;

    static {
        // 配置 HikariCP 连接池
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DatabaseConfig.DB_URL);
        config.setUsername(DatabaseConfig.DB_USERNAME);
        config.setPassword(DatabaseConfig.DB_PASSWORD);
        config.setMaximumPoolSize(10); // 设置最大连接数

        // 其他可选配置
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        dataSource = new HikariDataSource(config);
    }

    /**
     * 获取数据库连接
     *
     * @return Connection 对象
     * @throws SQLException SQL 异常
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * 关闭数据源
     */
    public static void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
    
    /**
     * 获取 HikariCP 数据源实例
     * @return
     */
    public static HikariDataSource getDataSource() {
        return dataSource;
    }
}