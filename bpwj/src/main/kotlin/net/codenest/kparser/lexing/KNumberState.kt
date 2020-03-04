package net.codenest.kparser.lexing

import kotlin.math.pow

/**
 * A NumberState object returns a number from a reader. This state's idea of a number allows an optional, initial
 * minus sign, followed by one or more digits. A decimal point and another string of digits may follow these digits.
 */
object KNumberState: KTokenizerState {

    override fun nextToken(ch: Char, reader: CharReader): KToken {
        require(isDigit(ch.toInt()) || ch == '-')

        var isNegative = false
        var next = ch.toInt()
        var value = 0.0

        // get negative sign
        if (next == '-'.toInt()) {
            isNegative = true
            next = reader.read()
            if (next == -1 || !isDigit(next)) {
                reader.unread(next)
                return KSymbolState.nextToken('-', reader)
            }
        }
        if (!isDigit(next)) return KSymbolState.nextToken(next.toChar(), reader)

        // get integer part
        var endWithComma = false
        while (isDigit(next) || isComma(next)) {
            endWithComma = isComma(next)
            if (isDigit(next)) value = value * 10 + (next - '0'.toInt())
            next = reader.read()
            if (next == -1) return getTokenNumber(isNegative, value)
        }
        if (endWithComma) {
            reader.unread(next)
            reader.unread(',')
            return getTokenNumber(isNegative, value)
        }

        // get fraction part
        if (next == '.'.toInt()) {
            next = reader.read()
            if (next == -1 || !isDigit(next)) {
                reader.unread(next)
                reader.unread('.')
                return getTokenNumber(isNegative, value)
            }

            var place = 0.1
            while (isDigit(next)) {
                value += (next - '0'.toInt()) * place
                place *= 0.1
                next = reader.read()
            }
        }

        // check scientific notation
        if (isExp(next)) {
            value *= 10.0.pow(getExpValue(next, reader))
            return getTokenNumber(isNegative, value)
        }

        reader.unread(next)
        return getTokenNumber(isNegative, value)
    }

    private fun getExpValue(exp: Int, reader: CharReader): Double {
        var isNegative = false
        var value = 0.0

        var next = reader.read()
        if (next == -1) {
            reader.unread(exp)
            return 0.0
        }

        // read negative sign
        if (next == '-'.toInt()) {
            isNegative = true
            next = reader.read()
            if (next == -1) {
                reader.unread('-')
                reader.unread(exp)
                return 0.0
            }
        }

        // the first letter after exp or '-' is not number
        if (!isDigit(next)) {
            reader.unread(next)
            if (isNegative) reader.unread('-')
            reader.unread(exp)
            return 0.0
        }

        // read exp value
        while (isDigit(next)) {
            value = value * 10 + (next - '0'.toInt())
            next = reader.read()
        }

        return if (isNegative) -value else value
    }

    private fun getTokenNumber(isNegative: Boolean, value: Double) =
            KToken(KTokenType.TT_NUMBER, "", if (isNegative) -value else value)

    private fun isDigit(c: Int) = c in '0'.toInt()..'9'.toInt()

    private fun isComma(c: Int) = c == ','.toInt()

    private fun isExp(c: Int) = c == 'e'.toInt() || c == 'E'.toInt()
}