package net.codenest.kparser.lexing


class KWhitespaceState : KTokenizerState {
    override fun nextToken(ch: Char, tokenizer: KTokenizer): KToken {
        require(tokenizer.getState(ch.toInt()) is KWhitespaceState)

        val reader = tokenizer.reader
        var ch = reader.read()
        while (ch != -1 && isWhitespace(ch.toChar(), tokenizer)) {
            ch = reader.read()
        }
        if (ch != -1) {
            reader.unread(ch.toChar())
        }

        return tokenizer.nextToken()
    }

    private fun isWhitespace(ch: Char, tokenizer: KTokenizer) = tokenizer.getState(ch.toInt()) is KWhitespaceState
}