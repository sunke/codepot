package net.codenest.kparser.parsing

import net.codenest.kparser.lexing.KToken

class KWord(level: Int = 0) : KTerminal("Word", level) {

    override fun qualify(token: KToken) = token.isWord()
}