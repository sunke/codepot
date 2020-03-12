package net.codenest.kparser.parsing

import net.codenest.kparser.lexing.KToken

class KLiteral(val literal: String, level: Int = 0) : KTerminal("Literal", level) {

    override fun qualify(token: KToken) = token.isWord() && literal == token.sval
}