package org.example.springbootwebsocketio.demos.component;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/ws/status/{taskId}")
public class TaskStatusEndpoint {

    private static Map<String, Set<Session>> taskSessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("taskId") String taskId) {
        taskSessions.computeIfAbsent(taskId, k -> ConcurrentHashMap.newKeySet()).add(session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("taskId") String taskId) {
        Set<Session> sessions = taskSessions.get(taskId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                taskSessions.remove(taskId);
            }
        }
    }

    // 工具方法：向指定 taskId 的所有连接推送消息
    public static void sendStatus(String taskId, String message) {
        Set<Session> sessions = taskSessions.get(taskId);
        if (sessions != null) {
            sessions.forEach(s -> {
                try {
                    s.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    // handle error
                }
            });
        }
    }
}