package net.codenest.kparser.lexing


object KSlashState : KTokenizerState {

    override fun nextToken(ch: Char, reader: KCharReader): KToken {
        require(ch == '/')

        var next = reader.read()
        if (next == -1) return KSymbolState.nextToken('/', reader)

        // '/*' state ignores everything up to a closing '*/', and then returns the tokenizer's next token.

        if (next == '*'.toInt()) {
            var prev = 0
            val str = StringBuilder("/*")
            next = reader.read()
            while (next != -1) {
                str.append(next.toChar())
                if (prev == '*'.toInt() && next == '/'.toInt()) {
                    break;
                }
                prev = next
                next = reader.read()
            }
            if (next == -1) throw Exception("Unmatched slash symbol: $str")
            return KToken.SKIP
        }

        //  '//' state ignores everything up to an end-of-line and returns the tokenizer's next token.
        if (next == '/'.toInt()) {
            next = reader.read()
            while (next != '\n'.toInt() && next != '\r'.toInt() && next != -1) {
                next = reader.read()
            }
            return KToken.SKIP
        }

        reader.unread(next)
        return KSymbolState.nextToken('/', reader)
    }
}