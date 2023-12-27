@file:OptIn(ExperimentalMaterial3Api::class)

package com.pomolist.core.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pomolist.feature_task.presentation.home.HomeScreen
import com.pomolist.feature_task.presentation.register_edit_task.RegisterScreen
import com.pomolist.feature_task.presentation.timer.TimerScreen
import com.pomolist.feature_task.presentation.timer.TimerScreenTest
import com.pomolist.feature_task.presentation.timer.TimerTaskScreen

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen(navController)
        }
        composable(
            route = Screen.TimerScreen.route
        ) {
            TimerScreen(navController)
        }
        composable(
            route = Screen.TimerScreenTest.route
        ) {
            TimerScreenTest(navController)
        }
        composable(
            route = Screen.TimerTaskScreen.route,
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry  ->
            TimerTaskScreen(navController)
        }
        composable(
            route = Screen.RegisterScreen.route,
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry  ->
            RegisterScreen(navController)
        }
    }
}