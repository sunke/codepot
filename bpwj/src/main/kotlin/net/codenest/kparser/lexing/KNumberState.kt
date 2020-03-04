package net.codenest.kparser.lexing

/**
 * A NumberState object returns a number from a reader. This state's idea of a number allows an optional, initial
 * minus sign, followed by one or more digits. A decimal point and another string of digits may follow these digits.
 */
object KNumberState: KTokenizerState {

    override fun nextToken(ch: Char, reader: CharReader): KToken {
        require(isDigit(ch.toInt()) || ch == '-')

        var isNegative = false
        var isComma = false
        var next = ch.toInt()
        var value = 0.0

        // get negative sign
        if (next == '-'.toInt()) {
            isNegative = true
            next = reader.read()
            if (next == -1) return KSymbolState.nextToken('-', reader)
        }
        if (!isDigit(next)) {
            return if (isNegative) {
                reader.unread(next.toChar())
                KSymbolState.nextToken('-', reader)
            } else {
                KSymbolState.nextToken(next.toChar(), reader)
            }
        }

        // get integer part
        while (isDigit(next) || next == ','.toInt()) {
            isComma = next == ','.toInt()
            if (isDigit(next)) value = value * 10 + (next - '0'.toInt())
            next = reader.read()
            if (next == -1) return getTokenNumber(isNegative, value)
        }
        if (isComma) {
            reader.unread(next.toChar())
            reader.unread(',')
            return getTokenNumber(isNegative, value)
        }

        // get fraction part
        if (next == '.'.toInt()) {
            next = reader.read()
            if (next == -1) {
                reader.unread('.')
                return getTokenNumber(isNegative, value)
            }
            if (!isDigit(next)) {
                reader.unread(next.toChar())
                reader.unread('.')
                return getTokenNumber(isNegative, value)
            }

            var place = 0.1
            while (isDigit(next)) {
                value += (next - '0'.toInt()) * place
                place *= 0.1
                next = reader.read()
            }
            if (next == -1) return getTokenNumber(isNegative, value)
        }
        reader.unread(next.toChar())
        return getTokenNumber(isNegative, value)
    }

    private fun getTokenNumber(isNegative: Boolean, value: Double) =
            KToken(KTokenType.TT_NUMBER, "", if (isNegative) -value else value)

    private fun isDigit(c: Int) = c in '0'.toInt()..'9'.toInt()
}