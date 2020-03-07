package net.codenest.kparser.lexing


object KWordState : KTokenizerState {

    override fun nextToken(ch: Char, reader: CharReader): KToken {
        require(KTokenizerStateTable.getState(ch, null) is KWordState)

        var next = reader.read()
        val str = StringBuilder(ch.toString())
        while (next != -1 && isWordCharacter(next.toChar())) {
            str.append(next.toChar())
            next = reader.read()
        }
        reader.unread(next)

        return KToken(KTokenType.TT_WORD, str.toString(), 0.0)
    }

    private fun isWordCharacter(ch: Char): Boolean {
        if (KTokenizerStateTable.getState(ch, null) is KWordState) return true
        if (ch == '\'' || ch == '-' || ch == '_') return true
        if (ch in '0'..'9') return true

        return false
    }
}