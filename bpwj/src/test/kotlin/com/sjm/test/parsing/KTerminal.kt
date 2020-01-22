package com.sjm.test.parsing

import com.sjm.test.lexing.KToken


abstract class KTerminal(name: String, level: Int = 0, private var discard: Boolean = false) : KParser<KToken>(name, level) {

    fun discard(): KTerminal {
        discard = true
        return this
    }

    override fun match(assemblies: List<KAssembly<KToken>>): List<KAssembly<KToken>> {
        val out = mutableListOf<KAssembly<KToken>>()
        assemblies.filter { it.hasMoreItem() && qualify(it.peekItem()!!) }
                .forEach {
                    val clone = it.clone()
                    val next = clone.nextItem()!!
                    if (!discard) clone.push(next)
                    out.add(clone)
                }
        return out
    }

    abstract fun qualify(token: KToken): Boolean
}