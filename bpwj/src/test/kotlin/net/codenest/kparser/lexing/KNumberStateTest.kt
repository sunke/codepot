package net.codenest.kparser.lexing

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.StringReader

internal class KNumberStateTest: KTokenStateTest(state = KNumberState) {

    @Test
    fun testNormalNumber() {
        assertToken(KToken(12345.0), "12345")
        assertToken(KToken(42.675), "42.675")
        assertToken(KToken(-0.124), "-0.124")
        assertToken(KToken( 0.0), "-0.0")
    }

    @Test
    fun testNumberComma() {
        assertToken(KToken(12345.0), "12,345")
        assertToken(KToken(4342.675), "4,342.675")
    }

    @Test
    fun testScientificNotation() {
        assertToken(KToken(602.0), "6.02e2")
        assertToken(KToken(0.016), "1.6E-2")

        assertToken(KToken(-5.0), "-5e", "e")
        assertToken(KToken(23.0), "23e-", "e-")
    }

    @Test
    fun testValidateNumber() {
        assertToken(KToken(4.0), "4.a", ".a")
        assertToken(KToken(12.0), "12 t", " t")
        assertToken(KToken('-'), "-a", "a")
        assertToken(KToken('-'), "-.123", ".123")
        assertToken(KToken('-'), "-")
        assertToken(KToken(6.0), "6,b", ",b")
        assertToken(KToken(1.23), "1.23,6b", ",6b")
        assertToken(KToken('-'), "-,234", ",234")
    }
}