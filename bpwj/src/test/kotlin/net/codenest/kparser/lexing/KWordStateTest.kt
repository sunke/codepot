package net.codenest.kparser.lexing

import org.junit.jupiter.api.Test

class KWordStateTest : KTokenStateTest(state = KWordState)  {

    @Test
    fun testWord() {
        assertToken(KToken("hello"), "hello world", " world")
        assertToken(KToken("Peter's"), "Peter's book", " book")
        assertToken(KToken("Jan-Willem"), "Jan-Willem Schut", " Schut")
        assertToken(KToken("test_1"), "test_1, test_2", ", test_2")
    }
}