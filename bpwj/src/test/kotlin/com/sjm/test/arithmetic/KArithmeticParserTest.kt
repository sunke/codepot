package com.sjm.test.arithmetic

import org.apache.log4j.PropertyConfigurator
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeAll

class KArithmeticParserTest {

    @BeforeAll
    fun setup() {
        PropertyConfigurator.configure("log4j.properties");
    }

    @Test
    fun testAssociativity() {
        assertEquals(3.0, calculate("7 - 3 - 1"))
        assertEquals(2.0, calculate("2 ^ 1 ^ 4"))
    }

    @Test
    fun testIllegalExpression() {
        assertImproperlyFormed("7 / ")
        assertImproperlyFormed("7 ++ 6 ")
        assertImproperlyFormed("7 **/ 2 ")
        assertImproperlyFormed("7^*2")
    }

    private fun assertImproperlyFormed(exp: String) {
        val exception = assertThrows(RuntimeException::class.java) { calculate(exp) }
        assertEquals(ERR_IMPROPERLY_FORMED, exception.message)
    }

    @Test
    fun testInteger() {
        assertEquals(42.0, calculate("42"))
    }

    @Test
    fun testParentheses() {
        assertEquals(18.0, calculate("((3 * 7) + (11 * 3)) / 3"))
    }

    @Test
    fun testPrecedence() {
        assertEquals(7.0, calculate("7 - 3 * 2 + 6"))
        assertEquals(2.0, calculate("2^1^4"))
        assertEquals(512.0, calculate("2^3^2"))
        assertEquals(1512.0, calculate("1000+2*2^3^2/2"))
        assertEquals(36.0, calculate("3*2^2*3"))
    }
}