package net.codenest.kparser.lexing

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.StringReader

internal class KNumberStateTest {

    private val numberState = KNumberState

    @Test
    fun testParseNumber() {
        assertToken(KToken(KTokenType.TT_NUMBER, "", 12345.0), "12345")
        assertToken(KToken(KTokenType.TT_NUMBER, "", 42.675), "42.675")
        assertToken(KToken(KTokenType.TT_NUMBER, "", -0.124), "-0.124")
        assertToken(KToken(KTokenType.TT_NUMBER, "", 0.0), "-0.0")
    }

    @Test
    fun testNumberComma() {
        assertToken(KToken(KTokenType.TT_NUMBER, "", 12345.0), "12,345")
        assertToken(KToken(KTokenType.TT_NUMBER, "", 4342.675), "4,342.675")
    }

    @Test
    fun testScientificNotation() {
        assertToken(KToken(KTokenType.TT_NUMBER, "", 602.0), "6.02e2")
        assertToken(KToken(KTokenType.TT_NUMBER, "", 0.016), "1.6E-2")

        assertToken(KToken(KTokenType.TT_NUMBER, "", -5.0), "-5e", "e")
        assertToken(KToken(KTokenType.TT_NUMBER, "", 23.0), "23e-", "e-")
    }

    @Test
    fun testValidateNumber() {
        assertToken(KToken(KTokenType.TT_NUMBER, "", 4.0), "4.a", ".a")
        assertToken(KToken(KTokenType.TT_SYMBOL, "-", 0.0), "-a", "a")
        assertToken(KToken(KTokenType.TT_SYMBOL, "-", 0.0), "-.123", ".123")
        assertToken(KToken(KTokenType.TT_SYMBOL, "-", 0.0), "-")
        assertToken(KToken(KTokenType.TT_NUMBER, "", 6.0), "6,b", ",b")
        assertToken(KToken(KTokenType.TT_NUMBER, "", 1.23), "1.23,6b", ",6b")
        assertToken(KToken(KTokenType.TT_SYMBOL, "-", 0.0), "-,234", ",234")
    }

    private fun assertToken(expect: KToken, str: String, expectRest: String = "") {
        val reader = CharReader(StringReader(str))
        assertEquals(expect, numberState.nextToken(reader.read().toChar(), reader))
        assertEquals(expectRest, readRest(reader))
    }

    private fun readRest(reader: CharReader): String {
        var rest = ""
        var ch = reader.read()
        while (ch != -1) {
            rest += ch.toChar().toString()
            ch = reader.read()
        }
        return rest
    }
}