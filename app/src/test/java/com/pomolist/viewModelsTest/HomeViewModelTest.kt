package com.pomolist.viewModelsTest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pomolist.feature_task.domain.model.Task
import com.pomolist.feature_task.domain.repository.TaskRepository
import com.pomolist.feature_task.presentation.home.HomeEvent
import com.pomolist.feature_task.presentation.home.screens.HomeViewModel
import com.pomolist.feature_task.presentation.register_edit_task.screens.RegisterViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsNot.not
import org.hamcrest.core.IsNull.nullValue
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.Flow

@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockRepository: TaskRepository

    private lateinit var viewModel: HomeViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(mockRepository)
    }

    @Test
    fun test_home_tasks() = runTest(UnconfinedTestDispatcher()) {
        val task1 = Task(1, "Aee 3", "Fazer alguma coisa", "1 - Baixa", "12/03/2024", "10:00")
        val task2 = Task(2, "Aee 4", "Fazer absolutamente nada", "4 - Maxima", "12/03/2024", "10:00")

        val listTasks = listOf(task1, task2)
        val mockTasks = flowOf(listTasks)

        `when`(mockRepository.getAllTasks()).thenReturn(mockTasks)

        viewModel.getRepositories()

        testDispatcher.scheduler.advanceUntilIdle()
        val state = viewModel.state.value.tasks

        assertEquals(listTasks, state)
    }

    @Test
    fun test_delete_task() = runTest(UnconfinedTestDispatcher()) {
        val task1 = Task(1, "Aee 3", "Fazer alguma coisa", "1 - Baixa", "12/03/2024", "10:00")

        val result = viewModel.onEvent(HomeEvent.DeleteTask(task1))

        assertThat(result, not(nullValue()));
    }

    @After
    fun close() {
        Dispatchers.shutdown()
    }
}
