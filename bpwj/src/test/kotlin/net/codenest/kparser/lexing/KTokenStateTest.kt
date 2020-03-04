package net.codenest.kparser.lexing

import org.junit.jupiter.api.Assertions
import java.io.StringReader

open class KTokenStateTest(var state: KTokenizerState) {

    fun assertToken(expect: KToken, str: String, expectRest: String = "") {
        val reader = CharReader(StringReader(str))
        Assertions.assertEquals(expect, state.nextToken(reader.read().toChar(), reader))
        Assertions.assertEquals(expectRest, readRest(reader))
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
