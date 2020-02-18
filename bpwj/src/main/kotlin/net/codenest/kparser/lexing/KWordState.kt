package net.codenest.kparser.lexing


class KWordState : KTokenizerState {
    override fun nextToken(ch: Char, tokenizer: KTokenizer): KToken {
        require(tokenizer.getState(ch.toInt()) is KWordState)

        val reader = tokenizer.reader
        val str = StringBuilder(ch.toChar().toString())
        var c = reader.read()
        while (c != -1 && isWordCharacter(c.toChar(), tokenizer)) {
            str.append(c.toChar())
            c = reader.read()
        }
        if (c != -1) {
            reader.unread(c.toChar())
        }

        return KToken(KTokenType.TT_WORD, str.toString(), 0.0);
    }

    private fun isWordCharacter(ch: Char, tokenizer: KTokenizer): Boolean {
        if (tokenizer.getState(ch.toInt()) is KWordState)
            return true

        if (ch == '\'' || ch == '-' || ch == '_')
            return true

        if (ch in '0'..'9')
            return true

        return false
    }
}