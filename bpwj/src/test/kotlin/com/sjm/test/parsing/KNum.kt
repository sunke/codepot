package com.sjm.test.parsing

import com.sjm.test.lexing.KToken

class KNum(level: Int = 0) : KTerminal("Num", level) {

    override fun qualify(token: KToken) = token.isNumber()
}