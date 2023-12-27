package com.pomolist.feature_task.presentation.timer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pomolist.feature_task.domain.model.Task
import com.pomolist.feature_task.domain.repository.TaskRepository
import com.pomolist.feature_task.presentation.register_edit_task.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    /* private val _pomodoro = MutableLiveData<Task>()
    val pomodoro: LiveData<Task> = _pomodoro

    fun getPomodoro(id: Int) = viewModelScope.launch {
        _pomodoro.postValue(taskRepository.getTaskById(id))
    } */

    private val _timeTask = mutableStateOf(TextFieldState())
    val timeTask: State<TextFieldState> = _timeTask

    var idTask: Int? = null

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

    /* var minuteTime: Long = 25
    var secondsTime: Long = 0

    fun alterTimeText() {
        // this.timeText.value = timeText
        minuteTime = minuteTime + 1
    }

    private var countDownTimer: CountDownTimer? = null
    private var userInputMinute = TimeUnit.MINUTES.toMillis(minuteTime)
    private var userInputSecond = TimeUnit.SECONDS.toMillis(secondsTime)
    var initialTotalTime = userInputMinute + userInputSecond

    var time = mutableStateOf(initialTotalTime)
    var timeText = mutableStateOf(time.value.timeFormat())
    val isPlaying = mutableStateOf(false)

    fun startCountDownTimer() = viewModelScope.launch {
        isPlaying.value = true
        countDownTimer = object : CountDownTimer(time.value, 1000L) {
            override fun onTick(currentTime: Long) {
                timeText.value = currentTime.timeFormat()
                time.value = currentTime
            }

            override fun onFinish() {
                timeText.value = initialTotalTime.timeFormat()
                isPlaying.value = false
            }
        }.start()
    }

    fun stopCountDownTimer() = viewModelScope.launch {
        isPlaying.value = false
        countDownTimer?.cancel()
    }

    fun resetCountDownTimer() = viewModelScope.launch {
        isPlaying.value = false
        countDownTimer?.cancel()
        timeText.value = initialTotalTime.timeFormat()
        time.value = initialTotalTime
    } */
}