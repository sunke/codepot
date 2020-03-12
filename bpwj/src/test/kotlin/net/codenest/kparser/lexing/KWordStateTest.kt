package net.codenest.kparser.lexing

import net.codenest.kparser.lexing.KToken.Companion.createWord
import org.junit.jupiter.api.Test

class KWordStateTest : KTokenStateTest(state = KWordState)  {

    @Test
    fun testWord() {
        assertToken(createWord("hello"), "hello world", " world")
        assertToken(createWord("Peter's"), "Peter's book", " book")
        assertToken(createWord("Jan-Willem"), "Jan-Willem Schut", " Schut")
        assertToken(createWord("test_1"), "test_1, test_2", ", test_2")
    }

    @Test
    fun testWordWithWhitespace() {
        (state as KWordState).setBlankAllowed()
        assertToken(createWord("Toasty Rita"), "Toasty Rita, Italian", ", Italian")
    }
}