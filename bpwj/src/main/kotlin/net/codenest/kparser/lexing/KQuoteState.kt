package net.codenest.kparser.lexing


/**
 * A QuoteState object returns a quoted string from a reader. This object will collect characters until it sees a match
 * to the character that the tokenizer used to switch to this state.
 */
object KQuoteState : KTokenizerState {
    override fun nextToken(ch: Char, reader: CharReader): KToken {
        val quoteChar = ch
        val str = StringBuilder(quoteChar.toChar().toString())
        var cin: Int?
        do {
            cin = reader.read()
            if (cin != -1) { str.append(cin.toChar()) }
        } while(cin != quoteChar.toInt() && cin != -1)

        // not matched quote until the end.
        if (cin == -1) {
            reader.unread(str.toString().toCharArray())
            return KSymbolState.nextToken(reader.read().toChar(), reader)
        }

        return KToken(KTokenType.TT_QUOTED, str.toString(), 0.0)
    }
}