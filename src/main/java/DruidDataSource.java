import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DruidDataSource {
    private static DataSource dataSource;

    static {
        try {
            Properties properties = new Properties();
            properties.setProperty("driverClassName", "com.mysql.cj.jdbc.Driver");
            properties.setProperty("url", DatabaseConfig.DB_URL);
            properties.setProperty("username", DatabaseConfig.DB_USERNAME);
            properties.setProperty("password", DatabaseConfig.DB_PASSWORD);
            properties.setProperty("initialSize", "5");
            properties.setProperty("maxActive", "20");
            properties.setProperty("minIdle", "5");
            properties.setProperty("maxWait", "60000");
            properties.setProperty("timeBetweenEvictionRunsMillis", "60000");
            properties.setProperty("minEvictableIdleTimeMillis", "300000");

            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if (dataSource instanceof com.alibaba.druid.pool.DruidDataSource) {
            ((com.alibaba.druid.pool.DruidDataSource) dataSource).close();
        }
    }
    
    /**
     * 获取 Druid 数据源实例
     * @return
     */
    public static DataSource getDataSource() {
        return dataSource;
    }
}