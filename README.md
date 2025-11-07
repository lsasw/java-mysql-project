# Java MySQL 多模块项目

这个项目包含了多个与Java、MySQL和Spring Boot相关的子项目。

## 项目结构

### 1. java-mysql (主项目)
基础的Java MySQL连接池比较项目，用于比较不同连接池（如HikariCP和Druid）的性能。

主要特性：
- HikariCP和Druid连接池的性能对比
- 多线程环境下的性能测试
- 简单易懂的实现方式

### 2. java-mysql-springboot
基于Spring Boot的MySQL连接池项目，集成了Druid连接池。

主要特性：
- Spring Boot框架集成
- Druid连接池配置
- RESTful API接口

### 3. springboot-websocketio
基于Spring Boot的WebSocket项目，演示了WebSocket通信功能。

主要特性：
- WebSocket实时通信
- 任务状态监控
- 前后端交互示例

### 4. task-monitor-frontend
基于Vue 3和Vite的任务监控前端项目。

主要特性：
- Vue 3组件化开发
- 实时数据显示
- WebSocket连接状态监控

## 如何运行

每个子项目都有其独立的运行方式，请参考各子项目目录下的说明文档。