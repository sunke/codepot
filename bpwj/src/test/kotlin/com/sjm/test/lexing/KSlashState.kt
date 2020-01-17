package com.sjm.test.lexing


class KSlashState : KTokenizerState {
    override fun nextToken(currentChar: Int, tokenizer: KTokenizer): KToken {
        return KToken(KTokenType.TT_NUMBER, "", 0.0)
    }
}