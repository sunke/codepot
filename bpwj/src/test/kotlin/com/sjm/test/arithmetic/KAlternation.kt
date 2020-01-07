package com.sjm.test.arithmetic

class KAlternation<T>(name: String = "Alternation") : KParser<T>() {

    private var subParsers = mutableListOf<KParser<T>>()

    fun add(parser: KParser<T>) = apply { subParsers.add(parser) }

    override fun match(assemblies: List<KAssembly<T>>): List<KAssembly<T>> {
        var out = mutableListOf<KAssembly<T>>()
        subParsers.forEach { out.addAll(it.matchAndAssemble(assemblies)) }
        return out
    }
}