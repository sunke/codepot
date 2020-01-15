package com.sjm.test.parse

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
    class State(var isNumber: Boolean = false, var isNegative: Boolean = false, var hasFraction: Boolean = false)

    override fun nextToken(currentChar: Int, reader: PushbackReader): KToken {
        val state = State()
        var cin = currentChar
        var value = 0.0

        if (cin == '-'.toInt()) {
            cin = reader.read()
            state.isNegative = true
        }
        value += readInteger(cin, reader, state)

        if (cin == '.'.toInt()) {
            cin = reader.read()
            state.hasFraction = true
            value += readFraction(cin, reader, state)
        }

        reader.unread(cin)

        return value(value, reader, state)
    }

    private fun value(value: Double, reader: PushbackReader, state: State): KToken {
        var v = value
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
        if (state.isNegative) {
            v = -v
        }
        return KToken(KTokenType.TT_NUMBER, "", v)
    }

    private fun readInteger(currentChar: Int, reader: PushbackReader, state: State): Int {
        var cin = currentChar
        var value = 0
        while (isDigit(cin)) {
            state.isNumber = true
            value = value * 10 + (cin - '0'.toInt())
            cin = reader.read()
        }
        return value
    }

    private fun readFraction(currentChar: Int, reader: PushbackReader, state: State): Double {
        var cin = currentChar
        var value = 0.0
        var divideBy = 1
        while (isDigit(cin)) {
            state.isNumber = true
            value = value * 10 + (cin - '0'.toInt())
            cin = reader.read()
            divideBy *= 10
        }
        value /= divideBy
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