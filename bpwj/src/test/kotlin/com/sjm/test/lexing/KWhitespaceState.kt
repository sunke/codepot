package com.sjm.test.lexing


class KWhitespaceState : KTokenizerState {
    override fun nextToken(ch: Int, tokenizer: KTokenizer): KToken {
        require(tokenizer.getState(ch) is KWhitespaceState)

        val reader = tokenizer.reader
        var ch = reader.read()
        while (ch != -1 && isWhitespace(ch, tokenizer)) {
            ch = reader.read()
        }
        if (ch != -1) {
            reader.unread(ch)
        }

        return tokenizer.nextToken()
    }

    private fun isWhitespace(ch: Int, tokenizer: KTokenizer) = tokenizer.getState(ch) is KWhitespaceState
}