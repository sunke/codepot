package net.codenest.kparser.parsing

import net.codenest.kparser.lexing.KToken
import net.codenest.kparser.lexing.KTokenType

class KSymbol(name: String = "Symbol", level: Int = 0, var symbol: KToken): KTerminal(name, level) {

    constructor(ch: Char, level: Int = 0): this(ch.toString(), level)

    constructor(str: String, level: Int = 0):
            this(name = "Symbol: $str", level = level, symbol = KToken(KTokenType.TT_SYMBOL, str, 0.0))

    override fun qualify(token: KToken) = symbol == token
}