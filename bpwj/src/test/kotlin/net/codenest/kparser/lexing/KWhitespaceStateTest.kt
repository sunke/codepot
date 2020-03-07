package net.codenest.kparser.lexing

import org.junit.jupiter.api.Test

class KWhitespaceStateTest : KTokenStateTest(state = KWhitespaceState)  {

    @Test
    fun testWhitespace() {
        assertToken(KToken.SKIP, "  ")
        assertToken(KToken.SKIP, "  x", "x")
        assertToken(KToken.SKIP, "\tx", "x")
    }
}