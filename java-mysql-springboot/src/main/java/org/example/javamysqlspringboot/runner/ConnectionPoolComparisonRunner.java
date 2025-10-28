package org.example.javamysqlspringboot.runner;

import org.example.javamysqlspringboot.service.ConnectionPoolComparisonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConnectionPoolComparisonRunner implements CommandLineRunner {

    private final ConnectionPoolComparisonService comparisonService;

    public ConnectionPoolComparisonRunner(ConnectionPoolComparisonService comparisonService) {
        this.comparisonService = comparisonService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("开始对比 HikariCP 和 Druid 连接池性能...\n");

        // 测试 HikariCP 性能
        System.out.println(comparisonService.testHikariCPPerformance());

        // 测试 Druid 性能
        System.out.println(comparisonService.testDruidPerformance());
    }
}