package com.pomolist.feature_task.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pomolist.core.navigation.Navigation
import com.pomolist.feature_task.data.local.AppDataBase
import com.pomolist.feature_task.data.local.TaskDAO
import com.pomolist.feature_task.data.repository.RepositoryImpl
import com.pomolist.feature_task.domain.repository.TaskRepository
import com.pomolist.feature_task.presentation.home.HomeViewModel
import com.pomolist.feature_task.presentation.register_edit_task.RegisterViewModel
import com.pomolist.feature_task.presentation.timer.TimerScreen
import com.pomolist.ui.theme.PomoListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /* private val homeViewModel: HomeViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val taskDAO: TaskDAO = AppDataBase.getInstance(this@MainActivity).taskDAO
                val repository: TaskRepository = RepositoryImpl(taskDAO)

                return HomeViewModel(repository) as T
            }
        }
    }

    private val registerViewModel: RegisterViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val taskDAO: TaskDAO = AppDataBase.getInstance(this@MainActivity).taskDAO
                val repository: TaskRepository = RepositoryImpl(taskDAO)
                val savedStateHandle = SavedStateHandle()

                return RegisterViewModel(repository, savedStateHandle) as T
            }
        }
    } */

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            PomoListTheme {
                Surface {
                    Navigation()
                }
            }
        }
    }
}