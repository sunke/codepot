package com.sjm.test.lexing


/**
 * A QuoteState object returns a quoted string from a reader. This object will collect characters until it sees a match
 * to the character that the tokenizer used to switch to this state.
 */
class KQuoteState : KTokenizerState {
    override fun nextToken(currentChar: Int, tokenizer: KTokenizer): KToken {
        val reader = tokenizer.reader

        val quoteChar = currentChar
        val str = StringBuilder(quoteChar.toChar().toString())
        var cin: Int?
        do {
            cin = reader.read()
            if (cin != -1) { str.append(cin.toChar()) }
        } while(cin != quoteChar && cin != -1)

        // not matched quote until the end.
        if (cin == -1) {
            reader.unread(str.toString().toCharArray())
            return tokenizer.symbolState.nextToken(reader.read(), tokenizer)
        }

        return KToken(KTokenType.TT_QUOTED, str.toString(), 0.0)
    }
}