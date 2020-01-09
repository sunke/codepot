package com.sjm.test.parse

import com.sjm.parse.tokens.Token

class KSymbol(var symbol: Token, name: String = "Symbol"): KTerminal(name) {

    constructor(ch: Char): this(ch.toString())
    constructor(str: String): this(Token(Token.TT_SYMBOL, str, 0.0))

    override fun qualify(token: Token) = symbol == token
}