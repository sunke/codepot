package com.sjm.test.arithmetic

import com.sjm.parse.tokens.Token

abstract class KTerminal(name: String = "Terminal"): KParser<Token>() {

    private var discard = false

    fun discard(): KTerminal {
        discard = true
        return this
    }

    override fun match(assemblies: List<KAssembly<Token>>): List<KAssembly<Token>> {
        var out = mutableListOf<KAssembly<Token>>()
        assemblies.forEach {
              if (it.hasMoreItem() && qualify(it.peekItem()!!)) {
                  val ay = it.clone()
                  if (!discard) ay.push(it.nextItem()!!)
                  out.add(ay)
             }
        }
        return out;
    }

    abstract fun qualify(token: Token): Boolean
}