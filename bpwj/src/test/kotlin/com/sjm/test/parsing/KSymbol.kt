package com.sjm.test.parsing

import com.sjm.test.lexing.KToken
import com.sjm.test.lexing.KTokenType

class KSymbol(name: String = "Symbol", level: Int = 0, var symbol: KToken): KTerminal(name, level) {

    constructor(ch: Char, level: Int): this(ch.toString(), level)
    constructor(str: String, level: Int): this(name = "Symbol: $str", level = level, symbol = KToken(KTokenType.TT_SYMBOL, str, 0.0))

    override fun qualify(token: KToken) = symbol == token
}