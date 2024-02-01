package com.pomolist.feature_task.presentation.register_edit_task.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pomolist.feature_task.domain.model.Task
import com.pomolist.feature_task.domain.repository.PomodoroRepository
import com.pomolist.feature_task.domain.repository.TaskRepository
import com.pomolist.feature_task.presentation.register_edit_task.RegisterEvent
import com.pomolist.feature_task.presentation.register_edit_task.components.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val pomodoroRepository: PomodoroRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var idTask: Int? = null

    // Task
    private val _nameTask = mutableStateOf(TextFieldState())
    val nameTask: State<TextFieldState> = _nameTask

    private val _descriptionTask = mutableStateOf(TextFieldState())
    val descriptionTask: State<TextFieldState> = _descriptionTask

    private val _priorityTask = mutableStateOf(TextFieldState())
    val priorityTask: State<TextFieldState> = _priorityTask

    private val _dateTask = mutableStateOf(TextFieldState())
    val dateTask: State<TextFieldState> = _dateTask

    private val _timeTask = mutableStateOf(TextFieldState())
    val timeTask: State<TextFieldState> = _timeTask

    private val _uiEventFlow = MutableSharedFlow<UiEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    var minute = mutableStateOf(0L)
    var second = mutableStateOf(0L)
    var amount = mutableStateOf(0L)

    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            if (id != -1) {
                viewModelScope.launch {
                    taskRepository.getTaskById(id)?.also {
                        idTask = id
                        _nameTask.value = nameTask.value.copy(
                            text = it.name
                        )
                        _descriptionTask.value = descriptionTask.value.copy(
                            text = it.description
                        )
                        _priorityTask.value = priorityTask.value.copy(
                            text = it.priority
                        )
                        _dateTask.value = dateTask.value.copy(
                            text = it.date
                        )
                        _timeTask.value = timeTask.value.copy(
                            text = it.time
                        )
                        minute.value = it.minutes
                        second.value = it.seconds
                        amount.value = it.amount
                    }
                }
            }
        }
    }

    fun onEvent(registerEvent: RegisterEvent) {
        when (registerEvent) {
            is RegisterEvent.EnteredName -> {
                _nameTask.value = nameTask.value.copy(
                    text = registerEvent.text
                )
            }
            is RegisterEvent.EnteredDescription -> {
                _descriptionTask.value = descriptionTask.value.copy(
                    text = registerEvent.text
                )
            }
            is RegisterEvent.EnteredPriority -> {
                _priorityTask.value = priorityTask.value.copy(
                    text = registerEvent.text
                )
            }
            is RegisterEvent.EnteredDate -> {
                _dateTask.value = dateTask.value.copy(
                    text = registerEvent.text
                )
            }
            is RegisterEvent.EnteredTime -> {
                _timeTask.value = timeTask.value.copy(
                    text = registerEvent.text
                )
            }
            is RegisterEvent.SaveTask -> {
                viewModelScope.launch {
                    taskRepository.insertTask(
                        Task(
                            id = idTask,
                            name = nameTask.value.text,
                            description = descriptionTask.value.text,
                            priority = priorityTask.value.text,
                            date = dateTask.value.text,
                            time = timeTask.value.text,
                            minutes = minute.value,
                            seconds = second.value,
                            amount = amount.value
                        )
                    )
                    _uiEventFlow.emit(UiEvent.SaveTask)
                }
            }

            else -> {}
        }
    }

    sealed class UiEvent {
        object SaveTask: UiEvent()
    }
}