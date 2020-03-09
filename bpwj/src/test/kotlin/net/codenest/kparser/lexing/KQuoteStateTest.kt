package net.codenest.kparser.lexing

import net.codenest.kparser.lexing.KToken.Companion.createQuote
import org.junit.jupiter.api.Test

class KQuoteStateTest : KTokenStateTest(state = KQuoteState)  {

    @Test
    fun testQuoteText() {
        assertToken(createQuote("\"abc\""), "\"abc\"def", "def")
        assertToken(createQuote("\"\""), "\"\"")
        assertToken(createQuote("\'abc\'"), "\'abc\'def", "def")
        assertToken(createQuote("\'\'"), "\'\'")
    }

    @Test
    fun testInvalidQuote() {
        assertThrow("\"abc", "Unmatched quote symbol: \"abc")
        assertThrow("\'abc", "Unmatched quote symbol: \'abc")
    }
}