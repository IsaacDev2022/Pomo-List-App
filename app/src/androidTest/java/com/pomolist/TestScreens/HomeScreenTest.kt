package com.pomolist.TestScreens

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pomolist.feature_task.presentation.MainActivity
import com.pomolist.feature_task.presentation.home.screens.HomeScreen
import com.pomolist.ui.theme.PomoListTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class HomeScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    private lateinit var navController: NavHostController

    @Before
    fun setup() {
        composeTestRule.activity.setContent {
            PomoListTheme {
                navController = rememberNavController()
                HomeScreen(navController = navController)
            }
        }
    }

    @Test
    fun test_click_items() {
        composeTestRule.apply {
            onNodeWithTag("openMenuIcon").performClick()
            onNodeWithTag("priorityChip").performClick()
        }
    }
}