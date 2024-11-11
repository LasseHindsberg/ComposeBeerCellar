package com.example.composebeercellar

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.composebeercellar.views.BeerListView
import org.junit.Rule
import org.junit.Test

class BeerListTest {

    @get:Rule
    val rule = createComposeRule()


    @Test
    fun testBeerListView() {
        rule.setContent { BeerListView(beers = listOf(), errorMessage = "") }

        // check if node exists
        rule.onNodeWithText("Grøn").assertExists()

        // do something
        rule.onNodeWithText("Grøn").performClick()

        // check if detailed view is displayed.



    }
}