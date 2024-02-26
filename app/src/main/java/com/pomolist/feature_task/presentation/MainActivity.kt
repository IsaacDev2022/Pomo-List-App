package com.pomolist.feature_task.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.pomolist.core.navigation.Navigation
import com.pomolist.ui.theme.PomoListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            PomoListTheme {
                Surface {
                    val navController = rememberNavController()
                    Navigation(navController)
                }
            }
        }
    }
}