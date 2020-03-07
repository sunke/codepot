package net.codenest.kparser.lexing

import org.junit.jupiter.api.Test

class KQuoteStateTest : KTokenStateTest(state = KQuoteState)  {

    @Test
    fun testQuoteText() {
        assertToken(KToken(KTokenType.TT_QUOTED, "\"abc\"", 0.0), "\"abc\"def", "def")
        assertToken(KToken(KTokenType.TT_QUOTED, "\"\"", 0.0), "\"\"")
        assertToken(KToken(KTokenType.TT_QUOTED, "\'abc\'", 0.0), "\'abc\'def", "def")
        assertToken(KToken(KTokenType.TT_QUOTED, "\'\'", 0.0), "\'\'")
    }

    @Test
    fun testInvalidQuote() {
        assertThrow("\"abc", "Unmatched quote symbol: \"abc")
        assertThrow("\'abc", "Unmatched quote symbol: \'abc")
    }
}