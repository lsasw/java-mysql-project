package org.example.javamysqlspringboot.controller;

import org.example.javamysqlspringboot.service.ConnectionPoolComparisonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/connection-pool")
public class ConnectionPoolController {

    private final ConnectionPoolComparisonService comparisonService;

    public ConnectionPoolController(ConnectionPoolComparisonService comparisonService) {
        this.comparisonService = comparisonService;
    }

    @GetMapping("/compare")
    public String compareConnectionPools() throws InterruptedException {
        StringBuilder result = new StringBuilder();
        result.append("开始对比 HikariCP 和 Druid 连接池性能...\n\n");
        
        // 测试 HikariCP 性能
        result.append(comparisonService.testHikariCPPerformance());
        
        // 测试 Druid 性能
        result.append(comparisonService.testDruidPerformance());
        
        return result.toString();
    }
}