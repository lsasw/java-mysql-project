package org.example.javamysqlspringboot.service;

import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class ConnectionPoolComparisonService {

    private static final int THREAD_COUNT = 20;
    private static final int TEST_COUNT = 100;

    private final DataSource hikariDataSource;
    private final DataSource druidDataSource;

    public ConnectionPoolComparisonService(DataSource hikariDataSource, DataSource druidDataSource) {
        this.hikariDataSource = hikariDataSource;
        this.druidDataSource = druidDataSource;
    }

    /**
     * 测试 HikariCP 连接池性能
     */
    public String testHikariCPPerformance() throws InterruptedException {
        StringBuilder result = new StringBuilder("=== HikariCP 性能测试 ===\n");

        CountDownLatch latch = new CountDownLatch(TEST_COUNT);
        long startTime = System.nanoTime();

        for (int i = 0; i < TEST_COUNT; i++) {
            new Thread(() -> {
                try (Connection connection = hikariDataSource.getConnection()) {
                    // 执行简单查询
                    PreparedStatement ps = connection.prepareStatement("SELECT 1");
                    ps.execute();
                    ps.close();
                } catch (SQLException e) {
                    synchronized (result) {
                        result.append("HikariCP连接出错: ").append(e.getMessage()).append("\n");
                    }
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        latch.await();
        long endTime = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

        result.append("HikariCP 完成 ").append(TEST_COUNT).append(" 次连接测试，耗时: ").append(duration).append(" ms\n\n");
        return result.toString();
    }

    /**
     * 测试 Druid 连接池性能
     */
    public String testDruidPerformance() throws InterruptedException {
        StringBuilder result = new StringBuilder("=== Druid 性能测试 ===\n");

        CountDownLatch latch = new CountDownLatch(TEST_COUNT);
        long startTime = System.nanoTime();

        for (int i = 0; i < TEST_COUNT; i++) {
            new Thread(() -> {
                try (Connection connection = druidDataSource.getConnection()) {
                    // 执行简单查询
                    PreparedStatement ps = connection.prepareStatement("SELECT 1");
                    ps.execute();
                    ps.close();
                } catch (SQLException e) {
                    synchronized (result) {
                        result.append("Druid连接出错: ").append(e.getMessage()).append("\n");
                    }
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        latch.await();
        long endTime = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

        result.append("Druid 完成 ").append(TEST_COUNT).append(" 次连接测试，耗时: ").append(duration).append(" ms\n\n");
        return result.toString();
    }
}