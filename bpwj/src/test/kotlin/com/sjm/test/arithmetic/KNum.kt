package com.sjm.test.arithmetic

import com.sjm.parse.tokens.Token

class KNum() : KTerminal("Num") {

    override fun qualify(token: Token) = token.isNumber
}