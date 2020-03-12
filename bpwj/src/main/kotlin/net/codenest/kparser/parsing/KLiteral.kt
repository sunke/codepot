package net.codenest.kparser.parsing

import net.codenest.kparser.lexing.KToken

class KLiteral(private val literal: String, private val ignoreCase: Boolean = false, level: Int = 0) : KTerminal("Literal", level) {

    override fun qualify(token: KToken) = token.isWord() && literal.equals(token.sval, ignoreCase)
}