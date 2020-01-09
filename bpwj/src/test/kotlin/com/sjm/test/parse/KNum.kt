package com.sjm.test.parse

import com.sjm.parse.tokens.Token

class KNum() : KTerminal("Num") {

    override fun qualify(token: Token) = token.isNumber
}