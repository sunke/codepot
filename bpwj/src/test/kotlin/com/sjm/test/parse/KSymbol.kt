package com.sjm.test.parse

import com.sjm.parse.tokens.Token

class KSymbol(name: String = "Symbol", level: Int = 0, var symbol: Token): KTerminal(name, level) {

    constructor(ch: Char, level: Int): this(ch.toString(), level)
    constructor(str: String, level: Int): this(name = "Symbol: $str", level = level, symbol = Token(Token.TT_SYMBOL, str, 0.0))

    override fun qualify(token: Token) = symbol == token
}