package com.example.tiptime

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.isToggleable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.tiptime.ui.theme.TipTimeTheme
import org.junit.Rule
import org.junit.Test
import java.text.NumberFormat

class TipUITests {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun calculate_20_percent_tip() {
        rule.setContent {
            TipTimeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TipTimeLayout()
                }
            }
        }
        rule.onNodeWithText("Bill Amount").performTextInput("10")
        rule.onNodeWithText("Tip Percentage").performTextInput("20")
        val expected = NumberFormat.getCurrencyInstance().format(2)
        rule.onNodeWithText("Tip Amount: $expected").assertExists(
            "No node with this text was found."
        )
    }

    @Test
    fun calculate_15_percent_tip_with_roundup() {
        rule.setContent {
            TipTimeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TipTimeLayout()
                }
            }
        }
        rule.onNodeWithText("Bill Amount").performTextInput("10.50")
        rule.onNodeWithText("Tip Percentage").performTextInput("15")
        rule.onNode(isToggleable()).performClick()
        val expected = NumberFormat.getCurrencyInstance().format(2)
        rule.onNodeWithText("Tip Amount: $expected").assertExists(
            "No node with this text was found."
        )
    }
}
