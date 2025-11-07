package org.example.springbootwebsocketio.demos.controller;

import org.example.springbootwebsocketio.demos.service.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TaskController {

    @Autowired
    private CalculationService calculationService;

    @PostMapping("/start-task")
    public ResponseEntity<String> startTask() {
        String taskId = UUID.randomUUID().toString();
        calculationService.performCalculation(taskId);
        return ResponseEntity.ok(taskId);
    }
}