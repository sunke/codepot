package com.sjm.test.lexing

import io.mockk.mockk
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.PushbackReader
import java.io.StringReader
import java.util.stream.Stream
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KTokenizerStateTest {
    val tokenizer = mockk<KTokenizer>()

    @ParameterizedTest
    @MethodSource("numberStateTestSource")
    fun testNumberState(str: String, value: Double, msg: String = "") {
        val reader = PushbackReader(StringReader(str.substring(1)), KTokenizer.DEFAULT_SYMBOL_MAX)
        val token = KNumberState().nextToken(str[0].toInt(), reader, tokenizer)
        assertEquals(KToken(KTokenType.TT_NUMBER, "", value), token, msg)
    }

    private fun numberStateTestSource(): Stream<Arguments> {
        return Stream.of(
                Arguments.of("12 a", 12.0, "Match integer token"),
                Arguments.of("-75", -75.0, "Match negative Integer token"),
                Arguments.of("3.1415926.", 3.1415926, "Match real number token"),
                Arguments.of("-8.926x", -8.926, "Match negative real number token")
        )
    }

    @ParameterizedTest
    @MethodSource("quoteStateTestSource")
    fun testQuoteState(str: String, value: String, msg: String = "") {
        val reader = PushbackReader(StringReader(str.substring(1)), KTokenizer.DEFAULT_SYMBOL_MAX)
        val token = KQuoteState().nextToken(str[0].toInt(), reader, tokenizer)
        assertEquals(KToken(KTokenType.TT_QUOTED, value, 0.0), token, msg)
    }

    private fun quoteStateTestSource(): Stream<Arguments> {
        return Stream.of(
                Arguments.of("'X'", "'X'", "Match single quote string"),
                Arguments.of("\"T\"...", "\"T\"", "Match double quote string")
        )
    }
}