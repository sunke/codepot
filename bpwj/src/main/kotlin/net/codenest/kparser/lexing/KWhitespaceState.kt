package net.codenest.kparser.lexing


object KWhitespaceState : KTokenizerState {
    override fun nextToken(ch: Char, reader: CharReader): KToken {
        var ch = reader.read()
        while (ch != -1 && isWhitespace(ch.toChar())) {
            ch = reader.read()
        }
        if (ch != -1) {
            reader.unread(ch.toChar())
        }

        return KToken.SKIP
    }

    private fun isWhitespace(ch: Char) = KTokenizerStateTable.getState(ch, null) is KWhitespaceState
}