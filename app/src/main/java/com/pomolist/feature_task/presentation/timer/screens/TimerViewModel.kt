package com.pomolist.feature_task.presentation.timer.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pomolist.feature_task.domain.repository.TaskRepository
import com.pomolist.feature_task.presentation.register_edit_task.components.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var minutes: Long = 0
    var seconds: Long = 0
    var amount: Long = 0

    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            if (id != -1) {
                viewModelScope.launch {
                    taskRepository.getTaskById(id)?.also {
                        minutes = it.minutes
                        seconds = it.seconds
                        amount = it.amount
                    }
                }
            }
        }
    }
}