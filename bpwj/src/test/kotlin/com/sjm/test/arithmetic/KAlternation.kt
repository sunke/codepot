package com.sjm.test.arithmetic

class KAlternation<T>() : KParser<T>() {

    private var subparsers = mutableListOf<KParser<T>>()

    override fun match(assemblies: List<KAssembly<T>>): List<KAssembly<T>> {
        var out = mutableListOf<KAssembly<T>>()
        subparsers.forEach { out.addAll(it.matchAndAssemble(assemblies)) }
        return out
    }

    fun add(parser: KParser<T>) = apply { subparsers.add(parser) }
}