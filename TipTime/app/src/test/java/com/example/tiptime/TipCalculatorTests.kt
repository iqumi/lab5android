package com.example.tiptime

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.NumberFormat

class TipCalculatorTests {

    @Test
    fun calculateTip_20PercentNoRoundup() {
        val amount = 10.00
        val tipPercent = 20.00
        val expected = NumberFormat.getCurrencyInstance().format(2)
        val actual = calculateTip(amount = amount, tipPercent = tipPercent, roundUp = false)
        assertEquals(expected, actual)
    }

    @Test
    fun calculateTip_15PercentWithRoundup() {
        val expected = NumberFormat.getCurrencyInstance().format(2)
        val actual = calculateTip(amount = 10.50, tipPercent = 15.0, roundUp = true)
        assertEquals(expected, actual)
    }

    @Test
    fun calculateTip_zeroAmount() {
        val expected = NumberFormat.getCurrencyInstance().format(0)
        val actual = calculateTip(amount = 0.0, tipPercent = 20.0, roundUp = false)
        assertEquals(expected, actual)
    }
}
