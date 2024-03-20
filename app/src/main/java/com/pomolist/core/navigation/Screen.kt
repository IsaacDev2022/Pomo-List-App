package com.pomolist.core.navigation

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home")
    object TasksCompletedScreen: Screen("tasksCompleted")
    object TimerScreen: Screen("timer")
    object TimerTaskScreen: Screen("timerTaskScreen?id={id}")
    object RegisterScreen: Screen("registerScreen?id={id}")
    fun passIdTimer(id: Int?): String {
        return "timerTaskScreen?id=$id"
    }
    fun passId(id: Int?): String {
        return "registerScreen?id=$id"
    }
}