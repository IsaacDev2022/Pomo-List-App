package com.pomolist.NavigationsTest

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.pomolist.core.navigation.Navigation
import com.pomolist.core.navigation.Screen
import com.pomolist.feature_task.presentation.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationAppTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    private lateinit var navController: NavHostController

    @Before
    fun setup() {
        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            Navigation(navController)
            hiltRule.inject()
        }
    }

    @Test
    fun assert_navigateTimerToHome() {
        composeTestRule.onNodeWithTag("homeIcon").performClick()

        val route = navController.currentBackStackEntry?.destination?.route

        Assert.assertEquals(route, Screen.HomeScreen.route)
    }

    @Test
    fun assert_navigateHomeToRegister() {
        composeTestRule.onNodeWithTag("homeIcon").performClick()
        composeTestRule.onNodeWithTag("openMenuIcon").performClick()

        val route = navController.currentBackStackEntry?.destination?.route

        Assert.assertEquals(route, Screen.HomeScreen.route)
    }
}