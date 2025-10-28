import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ConnectionPoolComparison {

    private static final int THREAD_COUNT = 20;
    private static final int TEST_COUNT = 100;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("开始对比 HikariCP 和 Druid 连接池性能...\n");

        // 测试 HikariCP 性能
        testHikariCPPerformance();

        // 测试 Druid 性能
        testDruidPerformance();

        // 关闭数据源
        HikariCPDataSource.closeDataSource();
        DruidDataSource.closeDataSource();
    }

    /**
     * 测试 HikariCP 连接池性能
     */
    private static void testHikariCPPerformance() throws InterruptedException {
        System.out.println("=== HikariCP 性能测试 ===");

        CountDownLatch latch = new CountDownLatch(TEST_COUNT);
        long startTime = System.nanoTime();

        for (int i = 0; i < TEST_COUNT; i++) {
            new Thread(() -> {
                try (Connection connection = HikariCPDataSource.getConnection()) {
                    // 执行简单查询
                    PreparedStatement ps = connection.prepareStatement("SELECT 1");
                    ps.execute();
                    ps.close();
                } catch (SQLException e) {
                    System.err.println("HikariCP连接出错: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        latch.await();
        long endTime = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

        System.out.println("HikariCP 完成 " + TEST_COUNT + " 次连接测试，耗时: " + duration + " ms\n");
    }

    /**
     * 测试 Druid 连接池性能
     */
    private static void testDruidPerformance() throws InterruptedException {
        System.out.println("=== Druid 性能测试 ===");

        CountDownLatch latch = new CountDownLatch(TEST_COUNT);
        long startTime = System.nanoTime();

        for (int i = 0; i < TEST_COUNT; i++) {
            new Thread(() -> {
                try (Connection connection = DruidDataSource.getConnection()) {
                    // 执行简单查询
                    PreparedStatement ps = connection.prepareStatement("SELECT 1");
                    ps.execute();
                    ps.close();
                } catch (SQLException e) {
                    System.err.println("Druid连接出错: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        latch.await();
        long endTime = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

        System.out.println("Druid 完成 " + TEST_COUNT + " 次连接测试，耗时: " + duration + " ms\n");
    }
}