package com.sjm.test.lexing

import java.io.PushbackReader

class KSlashState : KTokenizerState {
    override fun nextToken(currentChar: Int, reader: PushbackReader, tokenizer: KTokenizer): KToken {
        return KToken(KTokenType.TT_NUMBER, "", 0.0)
    }
}