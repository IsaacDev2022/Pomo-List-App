package com.pomolist.feature_task.presentation.home

import com.pomolist.feature_task.domain.model.Task

sealed class HomeEvent {
    data class DeleteTask(val task: Task): HomeEvent()
}