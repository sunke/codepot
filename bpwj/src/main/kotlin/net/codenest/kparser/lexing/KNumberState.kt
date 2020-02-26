package net.codenest.kparser.lexing

/**
 * A NumberState object returns a number from a reader. This state's idea of a number allows an optional, initial
 * minus sign, followed by one or more digits. A decimal point and another string of digits may follow these digits.
 */
object KNumberState: KTokenizerState {

    override fun nextToken(ch: Char, reader: CharReader): KToken {
        var isNumber = false
        var isNegative = false
        var hasFraction = false
        var next = ch.toInt()
        var value = 0.0

        // get negative sign
        if (ch == '-') {
            isNegative = true
            next = reader.read()
        }

        // get integer part
        while (isDigit(next)) {
            isNumber = true
            value = value * 10 + (next - '0'.toInt())
            next = reader.read()
        }

        // get fraction part
        if (next == '.'.toInt()) {
            hasFraction = true
            next = reader.read()
            var place = 0.1
            while (isDigit(next)) {
                isNumber = true
                value += (next - '0'.toInt()) * place
                place *= 0.1
                next = reader.read()
            }
        }

        if (!isNumber) {
            if (isNegative && hasFraction) {
                reader.unread('.')
                return KSymbolState.nextToken('-', reader)
            }
            if (isNegative) {
                return KSymbolState.nextToken('-', reader)
            }
            if (hasFraction) {
                return KSymbolState.nextToken('.', reader)
            }
        } else {
            reader.unread(next.toChar())
        }

        return KToken(KTokenType.TT_NUMBER, "", if (isNegative) -value else value)
    }

    private fun isDigit(c: Int) = c in '0'.toInt()..'9'.toInt()
}