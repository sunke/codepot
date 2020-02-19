package net.codenest.kparser.lexing

/**
 * A NumberState object returns a number from a reader. This state's idea of a number allows an optional, initial
 * minus sign, followed by one or more digits. A decimal point and another string of digits may follow these digits.
 */
object KNumberState: KTokenizerState {
    class Status(var currentChar: Int,
                 var isNumber: Boolean = false,
                 var isNegative: Boolean = false,
                 var hasFraction: Boolean = false)

    override fun nextToken(ch: Char, reader: CharReader): KToken {
        val state = Status(ch.toInt())
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

        if (state.isNumber) {
            reader.unread(state.currentChar.toChar())
        }

        return createToken(value, state, reader)
    }

    private fun createToken(value: Double, status: Status, reader: CharReader): KToken {
        if (!status.isNumber) {
            if (status.isNegative && status.hasFraction) {
                reader.unread('.')
                return KSymbolState.nextToken('-', reader)
            }
            if (status.isNegative) {
                return KSymbolState.nextToken('-', reader)
            }
            if (status.hasFraction) {
                return KSymbolState.nextToken('-', reader)
            }
        }
        return KToken(KTokenType.TT_NUMBER, "", if (status.isNegative) -value else value)
    }

    private fun readInteger(state: Status, reader: CharReader): Int {
        var value = 0
        while (isDigit(state.currentChar)) {
            state.isNumber = true
            value = value * 10 + (state.currentChar - '0'.toInt())
            state.currentChar = reader.read()
        }
        return value
    }

    private fun readFraction(state: Status, reader: CharReader): Double {
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

    private fun isDigit(c: Int) = c.toChar() in '0'..'9'
}