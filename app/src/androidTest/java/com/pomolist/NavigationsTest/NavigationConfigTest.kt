package com.pomolist.NavigationsTest

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.pomolist.core.navigation.Navigation
import com.pomolist.core.navigation.Screen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationConfigTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: NavHostController

    @Before
    fun setup() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            Navigation(navController)
            hiltRule.inject()
        }
    }

    @Test
    fun assert_screenMainDestination() {
        val route = navController.currentBackStackEntry?.destination?.route

        Assert.assertEquals(route, Screen.TimerScreen.route)
    }

    @Test
    fun assert_destinationStartRight() {
        val startDestination = navController.graph.startDestinationRoute
        val route = navController.currentBackStackEntry?.destination?.route

        Assert.assertEquals(route, startDestination)
    }
}