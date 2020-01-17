package com.sjm.test.lexing


/**
 * ```
 * The idea of a symbol is a character that stands on its own, such as an ampersand or a parenthesis. For example,
 * when tokenizing the expression (isReady)&(isWilling), a typical tokenizer would return 7 tokens, including one
 * for each parenthesis and one for the ampersand. Thus a series of symbols such as )&( becomes three tokens,
 * while a series of letters such as isReady becomes a single word token.
 *
 * Multi-character symbols are an exception to the rule that a symbol is a standalone character.  For example, a
 * tokenizer may want less-than-or-equals to tokenize as a single token. This class provides a method for
 * establishing which multi-character symbols an object of this class should treat as single symbols. This allows,
 * for example, <code>"cat <= dog"</code> to tokenize as three tokens, rather than splitting the less-than and
 * equals symbols into separate tokens.
 *
 * By default, this state recognizes the following multi-character symbols: !=, :-, <=, >=
 * ```
 */
class KSymbolState : KTokenizerState {
    override fun nextToken(currentChar: Int, tokenizer: KTokenizer): KToken {
        return KToken(KTokenType.TT_NUMBER, "", 0.0)
    }
}