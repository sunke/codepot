package com.sjm.test.parse.token

import java.io.PushbackReader

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
abstract class KTokenizerState {
    abstract fun nextToken(currentChar: Int, reader: PushbackReader): KToken
}

class KNumberState : KTokenizerState() {
    class State(var currentChar: Int,
                var isNumber: Boolean = false,
                var isNegative: Boolean = false,
                var hasFraction: Boolean = false)

    override fun nextToken(currentChar: Int, reader: PushbackReader): KToken {
        val state = State(currentChar)
        var value = 0.0

        if (state.currentChar == '-'.toInt()) {
            state.currentChar = reader.read()
            state.isNegative = true
        }
        value += readInteger(state, reader)

        if (state.currentChar == '.'.toInt()) {
            state.currentChar = reader.read()
            state.hasFraction = true
            value += readFraction(state, reader)
        }

        reader.unread(state.currentChar)

        return createToken(value, state, reader)
    }

    private fun createToken(value: Double, state: State, reader: PushbackReader): KToken {
        val symbolState = KSymbolState()
        if (!state.isNumber) {
            if (state.isNegative && state.hasFraction) {
                reader.unread('.'.toInt())
                return symbolState.nextToken('-'.toInt(), reader)
            }
            if (state.isNegative) {
                return symbolState.nextToken('-'.toInt(), reader)
            }
            if (state.hasFraction) {
                return symbolState.nextToken('-'.toInt(), reader)
            }
        }
        return KToken(KTokenType.TT_NUMBER, "", if (state.isNegative) -value else value)
    }

    private fun readInteger(state: State, reader: PushbackReader): Int {
        var value = 0
        while (isDigit(state.currentChar)) {
            state.isNumber = true
            value = value * 10 + (state.currentChar - '0'.toInt())
            state.currentChar = reader.read()
        }
        return value
    }

    private fun readFraction(state: State, reader: PushbackReader): Double {
        var value = 0.0
        var place = 0.1
        while (isDigit(state.currentChar)) {
            state.isNumber = true
            value += (state.currentChar - '0'.toInt()) * place
            place *= 0.1
            state.currentChar = reader.read()
        }
        return value
    }

    private fun isDigit(c: Int) = '0'.toInt() <= c && c <= '9'.toInt()
}

class KQuoteState : KTokenizerState() {
    override fun nextToken(currentChar: Int, reader: PushbackReader): KToken {
        return KToken(KTokenType.TT_NUMBER, "", 0.0)
    }
}

class KSymbolState : KTokenizerState() {
    override fun nextToken(currentChar: Int, reader: PushbackReader): KToken {
        return KToken(KTokenType.TT_NUMBER, "", 0.0)
    }
}

class KWhitespaceState : KTokenizerState() {
    override fun nextToken(currentChar: Int, reader: PushbackReader): KToken {
        return KToken(KTokenType.TT_NUMBER, "", 0.0)
    }
}

class KSlashState : KTokenizerState() {
    override fun nextToken(currentChar: Int, reader: PushbackReader): KToken {
        return KToken(KTokenType.TT_NUMBER, "", 0.0)
    }
}

class KWordState : KTokenizerState() {
    override fun nextToken(currentChar: Int, reader: PushbackReader): KToken {
        return KToken(KTokenType.TT_NUMBER, "", 0.0)
    }
}