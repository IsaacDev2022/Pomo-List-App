package com.pomolist

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pomolist.feature_task.presentation.MainActivity
import com.pomolist.feature_task.presentation.register_edit_task.screens.RegisterScreen
import com.pomolist.ui.theme.PomoListTheme
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class RegisterScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: NavHostController

    @Before
    fun setup() {
        composeTestRule.activity.setContent {
            PomoListTheme {
                navController = rememberNavController()
                RegisterScreen(navController = navController)
            }
        }
    }

    @Test
    fun test_click_items() {
        composeTestRule.apply {
            onNodeWithText("Confirmar").performClick()
            onNodeWithText("Confirmar").assertIsDisplayed()

            onNodeWithText("Pomodoro").performClick()
            onNodeWithText("Pomodoro").assertIsDisplayed()
        }
    }

    @Test
    fun test_inputs() {
        composeTestRule.apply {
            // Name Input Test
            val nameInputText = "Tarefa 1"
            onNodeWithTag("NameTF").performTextInput(nameInputText)
            onNodeWithTag("NameTF").assertTextContains(nameInputText)

            // Description Input Test
            val descriptionInputText = "Fazer alguma coisa"
            onNodeWithTag("DescriptionTF").performTextInput(descriptionInputText)
            onNodeWithTag("DescriptionTF").assertTextContains(descriptionInputText)

            // Date Input Test
            val dateInputText = "29/04/2024"
            onNodeWithTag("DateTF").performTextInput(dateInputText)

            // Time Input Test
            val timeInputText = "10:00 PM"
            onNodeWithTag("TimeTF").performTextInput(timeInputText)
        }
    }
}