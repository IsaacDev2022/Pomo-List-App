package com.pomolist.feature_task.domain.usecase

import com.pomolist.feature_task.domain.model.Task
import com.pomolist.feature_task.domain.repository.TaskRepository

class DeleteTask(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task) {
        taskRepository.deleteTask(task)
    }
}