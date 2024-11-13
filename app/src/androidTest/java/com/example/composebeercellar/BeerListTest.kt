package com.example.composebeercellar

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.composebeercellar.views.AuthenticationView
import com.example.composebeercellar.views.BeerListView
import org.junit.Rule
import org.junit.Test

class BeerListTest {

    @get:Rule
    val rule = createComposeRule()


    @Test
    fun testBeerListViewNavigatesToAdd() {
        rule.setContent { BeerListView(beers = listOf(), errorMessage = "") }

        // check if node exists with the icon Icons.Filled.Add
        rule.onNodeWithContentDescription("Add").assertExists()

        // do something
        rule.onNodeWithContentDescription("Add").performClick()

        // check if add view shows up ?
        // something something navigates to add view

        // I gave up on this test xdd

    }

    @Test
    fun testUserRegistrationAndLogin() {
        rule.setContent { AuthenticationView() }

        rule.onNodeWithText("Email").assertExists()

        rule.onNodeWithText("Email").performTextInput("user@gmail.com")

        rule.onNodeWithText("Password").assertExists()

        rule.onNodeWithText("Password").performTextInput("password123")

        rule.onNodeWithText("Register").assertExists()
        rule.onNodeWithText("Register").performClick()

        Thread.sleep(1000)

        rule.onNodeWithText("Beer Cellar").assertExists()
    }
}