package com.sjm.test.lexing


class KWordState : KTokenizerState {
    override fun nextToken(ch: Int, tokenizer: KTokenizer): KToken {
        require(tokenizer.getState(ch) is KWordState)

        val reader = tokenizer.reader
        val str = StringBuilder(ch.toChar().toString())
        var ch = reader.read()
        while (ch != -1 && isWordCharacter(ch, tokenizer)) {
            str.append(ch.toChar())
            ch = reader.read()
        }
        if (ch != -1) {
            reader.unread(ch)
        }

        return KToken(KTokenType.TT_WORD, str.toString(), 0.0);
    }

    private fun isWordCharacter(ch: Int, tokenizer: KTokenizer): Boolean {
        if (tokenizer.getState(ch) is KWordState)
            return true

        if (ch == '\''.toInt() || ch == '-'.toInt() || ch == '_'.toInt())
            return true

        if ('0'.toInt() <= ch && ch <= '9'.toInt())
            return true

        return false
    }
}