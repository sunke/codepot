package net.codenest.kparser.lexing

import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KTokenizerStateTest {

    @ParameterizedTest
    @MethodSource("numberStateTestSource")
    fun testNumberState(str: String, value: Double, msg: String = "") {
        val tokenizer = KTokenizer(str)
        val token = tokenizer.nextToken()
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
        val tokenizer = KTokenizer(str)
        val token = tokenizer.nextToken()
        assertEquals(KToken(KTokenType.TT_QUOTED, value, 0.0), token, msg)
    }

    private fun quoteStateTestSource(): Stream<Arguments> {
        return Stream.of(
                Arguments.of("'X'", "'X'", "Match single quote string"),
                Arguments.of("\"T\"...", "\"T\"", "Match double quote string")
        )
    }

    @ParameterizedTest
    @MethodSource("wordStateTestSource")
    fun testWordState(str: String, value: String, msg: String = "") {
        val tokenizer = KTokenizer(str)
        val token = tokenizer.nextToken()
        assertEquals(KToken(KTokenType.TT_WORD, value, 0.0), token, msg)
    }

    private fun wordStateTestSource(): Stream<Arguments> {
        return Stream.of(
                Arguments.of("hello world", "hello", "Match a word"),
                Arguments.of("alan's car", "alan's", "Match single quote in word"),
                Arguments.of("chapter01", "chapter01", "Match number in word"),
                Arguments.of("jan-willem", "jan-willem", "Match minus character in word"),
                Arguments.of("test_1, test_2", "test_1", "Match underscore character in word")
        )
    }

    @ParameterizedTest
    @MethodSource("whitespaceStateTestSource")
    fun testWhitespaceState(str: String, value: String, msg: String = "") {
        val tokenizer = KTokenizer(str)
        val token = tokenizer.nextToken()
        assertEquals(KToken(KTokenType.TT_EOF, "", 0.0), token, msg)
    }

    private fun whitespaceStateTestSource(): Stream<Arguments> {
        return Stream.of(
                Arguments.of(" ", "", "Match a single whitespace"),
                Arguments.of("  ", "", "Match multiple whitespaces"),
                Arguments.of("\t", "", "Match a tab")
        )
    }

    @ParameterizedTest
    @MethodSource("symbolStateTestSource")
    fun testSymbolState(str: String, value: String, msg: String = "") {
        val tokenizer = KTokenizer(str)
        val token = tokenizer.nextToken()
        assertEquals(KToken(KTokenType.TT_SYMBOL, value, 0.0), token, msg)
    }

    private fun symbolStateTestSource(): Stream<Arguments> {
        return Stream.of(
                Arguments.of("!=2", "!=", "Match unequal symbol"),
                Arguments.of(">=4", ">=", "Match big or equal symbol"),
                Arguments.of("<=9", "<=", "Match samll or equal symbol"),
                Arguments.of("#abc", "#", "Match a single character symbol")
        )
    }
}