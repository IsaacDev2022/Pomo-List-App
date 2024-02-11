package com.pomolist.feature_task.presentation.home.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pomolist.feature_task.domain.repository.TaskRepository
import com.pomolist.feature_task.presentation.home.HomeEvent
import com.pomolist.feature_task.presentation.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskRepository: TaskRepository
): ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        taskRepository.getAllTasks().onEach { tasks ->
            _state.value = state.value.copy(
                tasks = tasks
            )
        }.launchIn(viewModelScope)
    }

    // Método criado apenas para teste
    fun getRepositories() {
        taskRepository.getAllTasks().onEach { tasks ->
            _state.value = state.value.copy(
                tasks = tasks
            )
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.DeleteTask -> {
                viewModelScope.launch {
                    taskRepository.deleteTask(event.task)
                }
            }
        }
    }
}