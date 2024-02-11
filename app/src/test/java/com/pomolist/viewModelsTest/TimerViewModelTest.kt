package com.pomolist.viewModelsTest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pomolist.feature_task.domain.repository.TaskRepository
import com.pomolist.feature_task.presentation.home.screens.HomeViewModel
import com.pomolist.feature_task.presentation.timer.screens.TimerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class TimerViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockRepository: TaskRepository

    private lateinit var viewModel: TimerViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    private var savedStateHandle = SavedStateHandle()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = TimerViewModel(mockRepository, savedStateHandle)
    }

    @Test
    fun `Testando se o savedStateHandle retorna o id da tarefa`() = runTest {
        savedStateHandle
    }
}