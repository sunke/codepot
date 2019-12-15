package com.sjm.test.arithmetic

import com.sjm.parse.tokens.Token

class KSymbol(val symbol: Token) : KParser<Token>() {

    private var discard = false

    override fun match(assemblies: List<KAssembly<Token>>): List<KAssembly<Token>> {
        var out = mutableListOf<KAssembly<Token>>()
        assemblies.forEach {
            if (it.hasMoreElements() && qualifies(it.peek())) {
                var out = it // clone
                val t = out.nextElement()
                if (!discard) {
                    out.push(t!!)
                }
            }
        }
        return out;
    }

    fun discard() = apply { discard = true }

    private fun qualifies(token: Token?) = symbol == token

}