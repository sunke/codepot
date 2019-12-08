package com.sjm.test.arithmetic

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class ArithmeticParserTest {
    @Test
    fun testArithmeticParser() {
        assertResult("9^2 - 81       ", 0.0) // exponentiation
        assertResult("7 - 3 - 1      ", 3.0) // minus associativity
        assertResult("2 ^ 1 ^ 4      ", 2.0) // exp associativity
        assertResult("100 - 25*3     ", 25.0) // precedence
        assertResult("100 - 5^2*3    ", 25.0) // precedence
        assertResult("(100 - 5^2) * 3", 225.0) // parentheses
    }

    companion object {
        private fun assertResult(s: String, d: Double) {
            Assertions.assertEquals(d, ArithmeticParser.Companion.value(s))
        }
    }
}