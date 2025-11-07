<template>
  <div id="app">
    <h2>计算任务监控面板</h2>
    <button @click="startTask" :disabled="loading">
      {{ loading ? '任务进行中...' : '开始计算任务' }}
    </button>

    <div v-if="status" class="status-box">
      <h3>当前状态：</h3>
      <p>{{ status }}</p>
    </div>

    <div v-if="taskId" class="task-info">
      <p>任务ID: <code>{{ taskId }}</code></p>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
  name: 'App',
  setup() {
    const loading = ref(false)
    const status = ref('')
    const taskId = ref('')
    let socket = null

    const startTask = async () => {
      // 关闭旧连接（防止重复）
      if (socket) {
        socket.close()
      }

      loading.value = true
      status.value = ''
      taskId.value = ''

      try {
        // 1. 请求后端启动任务
        const response = await fetch('/start-task', {
          method: 'POST'
        })

        if (!response.ok) {
          throw new Error('启动任务失败')
        }

        const newTaskId = await response.text()
        taskId.value = newTaskId
        status.value = '任务已启动，等待进度...'

        // 2. 连接 WebSocket 接收状态
        // socket = new WebSocket(`ws://localhost:8080/ws/status/${newTaskId}`)
        socket = new WebSocket(`/ws/status/${newTaskId}`)

        socket.onmessage = (event) => {
          status.value = event.data
          // 如果任务完成或出错，可选地关闭 loading
          if (event.data.includes('完成') || event.data.includes('中断')) {
            loading.value = false
          }
        }

        socket.onerror = (error) => {
          console.error('WebSocket 错误:', error)
          status.value = '连接出错，请重试'
          loading.value = false
        }

        socket.onclose = () => {
          console.log('WebSocket 连接已关闭')
        }
      } catch (err) {
        console.error('启动任务异常:', err)
        status.value = '启动失败：' + err.message
        loading.value = false
      }
    }

    // 组件卸载时清理 WebSocket
    window.addEventListener('beforeunload', () => {
      if (socket) socket.close()
    })

    return {
      loading,
      status,
      taskId,
      startTask
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  text-align: center;
  margin-top: 60px;
}

button {
  padding: 12px 24px;
  font-size: 16px;
  cursor: pointer;
  background-color: #409eff;
  color: white;
  border: none;
  border-radius: 4px;
}

button:disabled {
  background-color: #a0cfff;
  cursor: not-allowed;
}

.status-box {
  margin-top: 20px;
  padding: 15px;
  background-color: #f0f9eb;
  border: 1px solid #e1f3d8;
  border-radius: 4px;
  display: inline-block;
}

.task-info {
  margin-top: 15px;
  font-size: 14px;
  color: #666;
}
</style>