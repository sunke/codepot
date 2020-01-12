package com.sjm.test.parse

import com.sjm.parse.tokens.Token

abstract class KTerminal(name: String, level: Int = 0, private var discard: Boolean = false) : KParser<Token>(name, level) {

    fun discard(): KTerminal {
        discard = true
        return this
    }

    override fun match(assemblies: List<KAssembly<Token>>): List<KAssembly<Token>> {
        val out = mutableListOf<KAssembly<Token>>()
        assemblies.filter { it.hasMoreItem() && qualify(it.peekItem()!!) }
                .forEach {
                    val clone = it.clone()
                    val next = clone.nextItem()!!
                    if (!discard) clone.push(next)
                    out.add(clone)
                }
        return out
    }

    abstract fun qualify(token: Token): Boolean
}