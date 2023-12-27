package com.pomolist.feature_task.domain.usecase

import com.pomolist.feature_task.domain.model.Task
import com.pomolist.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetAllTasks(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): Flow<List<Task>> {
        return taskRepository.getAllTasks()
    }
}