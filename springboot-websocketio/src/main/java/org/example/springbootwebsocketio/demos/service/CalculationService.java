package org.example.springbootwebsocketio.demos.service;

import org.example.springbootwebsocketio.demos.component.TaskStatusEndpoint;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CalculationService {

    @Async
    public void performCalculation(String taskId) {
        try {
            // 模拟长时间任务
            for (int i = 0; i <= 100; i += 10) {
                Thread.sleep(1000); // 模拟耗时操作
                TaskStatusEndpoint.sendStatus(taskId, "进度: " + i + "%");
            }
            TaskStatusEndpoint.sendStatus(taskId, "完成！");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            TaskStatusEndpoint.sendStatus(taskId, "任务被中断");
        }
    }
}