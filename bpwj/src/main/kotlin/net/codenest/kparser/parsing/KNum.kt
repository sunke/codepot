package net.codenest.kparser.parsing

import net.codenest.kparser.lexing.KToken

class KNum(level: Int = 0) : KTerminal("Num", level) {

    override fun qualify(token: KToken) = token.isNumber()
}