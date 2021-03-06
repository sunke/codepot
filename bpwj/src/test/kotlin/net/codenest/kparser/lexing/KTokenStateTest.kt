package net.codenest.kparser.lexing

import org.junit.jupiter.api.Assertions.assertEquals
import java.io.StringReader
import kotlin.test.assertFailsWith

abstract class KTokenStateTest(var state: KTokenizerState) {

    fun assertToken(expect: KToken, str: String, expectRest: String = "") {
        val reader = KCharReader(StringReader(str))
        assertEquals(expect, state.nextToken(reader.read().toChar(), reader))
        assertEquals(expectRest, readRest(reader))
    }

    fun assertThrow(str: String, exceptionMessage: String) {
        val exception = assertFailsWith<Exception> {
            val reader = KCharReader(StringReader(str))
            state.nextToken(reader.read().toChar(), reader)
        }
        assertEquals(exceptionMessage, exception.message)
    }

    private fun readRest(reader: KCharReader): String {
        var rest = ""
        var ch = reader.read()
        while (ch != -1) {
            rest += ch.toChar().toString()
            ch = reader.read()
        }
        return rest
    }
}
