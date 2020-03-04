package net.codenest.kparser.lexing

import org.junit.jupiter.api.Test

class KSlashStateTest: KTokenStateTest(state = KSlashState) {

    @Test
    fun testSlashStar() {
        assertToken(KToken.SKIP, "/*xxx*/")
        assertToken(KToken.SKIP, "/**/")
        assertToken(KToken.SKIP, "/*xxx*/end", "end")
        assertToken(KToken.SKIP, "/*1\nx2*/end", "end")
    }

    @Test
    fun testSlashSlash() {
        assertToken(KToken.SKIP, "//bla")
        assertToken(KToken.SKIP, "//1\n2", "2")
    }

    @Test
    fun testInvalidSlash() {
        assertToken(KToken(KTokenType.TT_SYMBOL, "/", 0.0), "/x", "x")
        assertToken(KToken.SKIP, "/*/")
    }
}