package com.pomolist.feature_task.domain.usecase

import com.pomolist.feature_task.domain.model.Task
import com.pomolist.feature_task.domain.repository.TaskRepository

class GetTaskById(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(id: Int): Task? {
        return taskRepository.getTaskById(id)
    }
}