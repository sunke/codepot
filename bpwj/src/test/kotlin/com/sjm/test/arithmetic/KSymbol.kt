package com.sjm.test.arithmetic

import com.sjm.parse.tokens.Token

class KSymbol(var symbol: Token): KTerminal("Symbol") {

    constructor(ch: Char): this(ch.toString())
    constructor(str: String): this(Token(Token.TT_SYMBOL, str, 0.0))

    override fun qualify(token: Token) = symbol == token
}