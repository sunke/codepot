package com.sjm.test.lexing

import java.io.PushbackReader

class KWhitespaceState : KTokenizerState {
    override fun nextToken(currentChar: Int, reader: PushbackReader, tokenizer: KTokenizer): KToken {
        var ch = reader.read()
        while (ch != -1 && isWhitespace(ch, tokenizer)) {
            ch = reader.read()
        }
        return tokenizer.nextToken()
    }

    private fun isWhitespace(ch: Int, tokenizer: KTokenizer) = tokenizer.getState(ch) is KWhitespaceState
}