package com.sjm.test.lexing

import java.io.PushbackReader

/**
 * A NumberState object returns a number from a reader. This state's idea of a number allows an optional, initial
 * minus sign, followed by one or more digits. A decimal point and another string of digits may follow these digits.
 */
class KNumberState: KTokenizerState {
    class Status(var currentChar: Int,
                 var isNumber: Boolean = false,
                 var isNegative: Boolean = false,
                 var hasFraction: Boolean = false)

    override fun nextToken(ch: Int, tokenizer: KTokenizer): KToken {
        require(tokenizer.getState(ch) is KNumberState)

        val reader = tokenizer.reader
        val state = Status(ch)
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

        return createToken(value, state, tokenizer)
    }

    private fun createToken(value: Double, status: Status, tokenizer: KTokenizer): KToken {
        val reader = tokenizer.reader

        if (!status.isNumber) {
            if (status.isNegative && status.hasFraction) {
                reader.unread('.'.toInt())
                return tokenizer.symbolState.nextToken('-'.toInt(), tokenizer)
            }
            if (status.isNegative) {
                return tokenizer.symbolState.nextToken('-'.toInt(), tokenizer)
            }
            if (status.hasFraction) {
                return tokenizer.symbolState.nextToken('-'.toInt(), tokenizer)
            }
        }
        return KToken(KTokenType.TT_NUMBER, "", if (status.isNegative) -value else value)
    }

    private fun readInteger(state: Status, reader: PushbackReader): Int {
        var value = 0
        while (isDigit(state.currentChar)) {
            state.isNumber = true
            value = value * 10 + (state.currentChar - '0'.toInt())
            state.currentChar = reader.read()
        }
        return value
    }

    private fun readFraction(state: Status, reader: PushbackReader): Double {
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