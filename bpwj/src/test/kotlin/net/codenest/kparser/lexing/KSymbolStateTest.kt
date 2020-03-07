package net.codenest.kparser.lexing

import org.junit.jupiter.api.Test

class KSymbolStateTest : KTokenStateTest(state = KSymbolState)  {

    @Test
    fun testSingleSymbol() {
        assertToken(getToken("="), "=2", "2")
        assertToken(getToken(">"), ">a", "a")
        assertToken(getToken("$"), "$12", "12")
        assertToken(getToken("#"), "#abc", "abc")
    }

    @Test
    fun testMultiCharSymbol() {
        assertToken(getToken("!="), "!=2", "2")
        assertToken(getToken(">="), ">=x", "x")
        assertToken(getToken("<="), "<=!", "!")
    }

    private fun getToken(sval: String): KToken {
        return KToken(KTokenType.TT_SYMBOL, sval, 0.0)
    }
}