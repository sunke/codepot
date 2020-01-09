package com.sjm.test.parse

import com.sjm.parse.tokens.Token

abstract class KTerminal(name: String, private var discard: Boolean = false) : KParser<Token>(name) {

    fun discard(): KTerminal {
        discard = true
        return this
    }

    override fun match(assemblies: List<KAssembly<Token>>): List<KAssembly<Token>> {
        val out = mutableListOf<KAssembly<Token>>()
        assemblies.filter { it.hasMoreItem() && qualify(it.peekItem()!!) }
                .forEach {
                    val next = it.nextItem()!!
                    val clone = it.clone()
                    if (!discard) clone.push(next)
                    out.add(clone)
                }
        return out
    }

    abstract fun qualify(token: Token): Boolean
}