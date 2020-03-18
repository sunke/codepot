package net.codenest.kparser.lexing


object KWhitespaceState : KTokenizerState {

    override fun nextToken(ch: Char, reader: KCharReader): KToken {
        require(isWhitespace(ch))

        var next = reader.read()
        while (next != -1 && isWhitespace(next.toChar())) {
            next = reader.read()
        }

        reader.unread(next)
        return KToken.SKIP
    }

    private fun isWhitespace(ch: Char) = KTokenizerStateTable.isWhitespace(ch)
}