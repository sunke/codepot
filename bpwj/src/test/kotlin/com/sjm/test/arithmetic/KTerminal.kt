package com.sjm.test.arithmetic

import com.sjm.parse.tokens.Token

abstract class KTerminal(name: String): KParser<Token>(name) {

    private var discard = false

    fun discard(): KTerminal {
        discard = true
        return this
    }

    override fun match(assemblies: List<KAssembly<Token>>): List<KAssembly<Token>> {
        var result = mutableListOf<KAssembly<Token>>()
        assemblies.filter{ it -> it.hasMoreItem() && qualify(it.peekItem()!!)}
                .forEach {
                    val next = it.nextItem()!!
                    val clone = it.clone()
                    if (!discard) clone.push(next)
                    result.add(clone)
                }
        return result;
    }

    abstract fun qualify(token: Token): Boolean
}