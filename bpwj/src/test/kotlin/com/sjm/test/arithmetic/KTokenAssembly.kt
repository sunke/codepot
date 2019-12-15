package com.sjm.test.arithmetic

import com.sjm.parse.tokens.Token
import com.sjm.parse.tokens.Tokenizer

class KTokenAssembly() : KAssembly<Token>() {
    private var tokens: List<Token> = listOf()

    constructor(string: String) : this() {
        val tokenizer = Tokenizer(string)
        var tok = tokenizer.nextToken()
        while (tok.ttype() != Token.TT_EOF) {
            tokens += tok
            tok = tokenizer.nextToken()
        }
    }

    override fun consumed(delimiter: String): String {
        return tokens.subList(0, elementsConsumed()).joinToString(separator = delimiter)
    }

    override fun remainder(delimiter: String): String {
        return tokens.subList(elementsConsumed() + 1, length()).joinToString(separator = delimiter)
    }

    override fun length() = tokens.size

    override fun peek(): Token? = if (elementsConsumed() >= length() - 1) null else tokens[index + 1]

    override fun nextElement(): Token? = if (elementsConsumed() >= length() - 1) null else tokens[index++]
}