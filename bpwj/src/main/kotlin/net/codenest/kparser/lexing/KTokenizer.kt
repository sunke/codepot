package net.codenest.kparser.lexing


import java.io.Reader
import java.io.StringReader

/**
 * ```
 * A tokenizer divides a string into tokens. This class is highly customizable with regard to exactly how this division
 * occurs, but it also has defaults that are suitable for many languages. This class assumes that the character values
 * read from the string lie in the range 0-255. For example, the Unicode value of a capital A is 65, so
 * ` System.out.println((char)65); ` prints out a capital A.
 *
 * The behavior of a tokenizer depends on its character state table. This table is an array of 256 `TokenizerState`
 * states. The state table decides which state to enter upon reading a character from the input string.
 *
 * For example, by default, upon reading an 'A', a tokenizer will enter a "word" state. This means the tokenizer will
 * ask a `WordState` object to consume the 'A', along with the characters after the 'A' that form a word. The state's
 * responsibility is to consume characters and return a complete token.
 *
 * The default table sets a SymbolState for every character from 0 to 255, and then overrides this with:
 *
 * From    To     State
 * 0     ' '    whitespaceState
 * 'a'    'z'    wordState
 * 'A'    'Z'    wordState
 * 160     255    wordState
 * '0'    '9'    numberState
 * '-'    '-'    numberState
 * '.'    '.'    numberState
 * '"'    '"'    quoteState
 * '\''   '\''    quoteState
 * '/'    '/'    slashState
 *
 * In addition to allowing modification of the state table, this class makes each of the states above available.
 * Some of these states are customizable. For example, wordState allows customization of what characters can
 * be part of a word, after the first character.
 *
 * @author Steven J. Metsker， Alan K. Sun

 * ```
 */
class KTokenizer(private val input: Reader) {
    private val stateTable = KTokenizerStateTable
    private var previousState: KTokenizerState? = null
    private val reader = CharReader(input, 4)

    fun nextToken(): KToken {
        val c = reader.read()
        return when {
            stateTable.isValidChar(c) -> nextToken(c.toChar())
            else -> KToken.END
        }
    }

    private fun nextToken(ch: Char): KToken {
        val state = stateTable.getState(ch, previousState)
        val next = state.nextToken(ch, reader)
        previousState = state
        return next
    }
}

/**
 * A tokenizerState returns a token, given a reader, an initial character read from the reader, and a tokenizer
 * that is conducting an overall tokenization of the reader. The tokenizer will typically have a character state
 * table that decides which state to use, depending on an initial character. If a single character is insufficient,
 * a state such as `SlashState` will read a second character, and may delegate to another state,
 * such as `SlashStarState`. This prospect of delegation is the reason that the `nextToken()`
 * method has a tokenizer argument.
 *
 * @author Steven J. Metsker, Alan K. Sun
 */
interface KTokenizerState {
    fun nextToken(ch: Char, reader: CharReader): KToken
}