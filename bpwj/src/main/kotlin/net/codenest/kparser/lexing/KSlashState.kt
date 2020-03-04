package net.codenest.kparser.lexing


object KSlashState : KTokenizerState {

    override fun nextToken(ch: Char, reader: CharReader): KToken {
        require(ch == '/')

        var next = reader.read()
        if (next == -1) return KSymbolState.nextToken('/', reader)

        // '/*' state ignores everything up to a closing '*/', and then returns the tokenizer's next token.
        if (next == '*'.toInt()) {
            var prev = 0
            next = reader.read()
            while (next != -1) {
                if (prev == '*'.toInt() && next == '/'.toInt()) {
                    break;
                }
                prev = next
                next = reader.read()
            }
            return KToken.SKIP
        }

        //  '//' state ignores everything up to an end-of-line and returns the tokenizer's next token.
        if (next == '/'.toInt()) {
            next = reader.read()
            while (next != '\n'.toInt() && next != '\r'.toInt() && next >= 0) {
                next = reader.read()
            }
            return KToken.SKIP
        }

        reader.unread(next)
        return KSymbolState.nextToken('/', reader)
    }
}