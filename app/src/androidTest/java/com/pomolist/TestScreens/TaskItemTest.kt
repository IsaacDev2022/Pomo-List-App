package com.pomolist.TestScreens

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pomolist.feature_task.domain.model.Task
import com.pomolist.feature_task.presentation.MainActivity
import com.pomolist.feature_task.presentation.home.components.TaskItem
import com.pomolist.ui.theme.PomoListTheme
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class TaskItemTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeTestRule.activity.setContent {
            PomoListTheme {
                TaskItem(task = Task(), onEditTask = { /*TODO*/ }, onDeleteTask = { /*TODO*/ }) {

                }
            }
        }
    }

    @Test
    fun test_click_items() {
        composeTestRule.apply {

        }
    }
}