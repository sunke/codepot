package net.codenest.kparser.lexing


object KWordState : KTokenizerState {
    override fun nextToken(ch: Char, reader: CharReader): KToken {
        val str = StringBuilder(ch.toChar().toString())
        var c = reader.read()
        while (c != -1 && isWordCharacter(c.toChar())) {
            str.append(c.toChar())
            c = reader.read()
        }
        if (c != -1) {
            reader.unread(c.toChar())
        }

        return KToken(KTokenType.TT_WORD, str.toString(), 0.0);
    }

    private fun isWordCharacter(ch: Char): Boolean {
        if (KTokenizerStateTable.getState(ch, null) is KWordState)
            return true

        if (ch == '\'' || ch == '-' || ch == '_')
            return true

        if (ch in '0'..'9')
            return true

        return false
    }
}