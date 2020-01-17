package com.sjm.test.parsing

import com.sjm.parse.tokens.Token

class KNum(level: Int = 0) : KTerminal("Num", level) {

    override fun qualify(token: Token) = token.isNumber
}