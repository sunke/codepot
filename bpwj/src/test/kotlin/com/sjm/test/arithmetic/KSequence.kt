package com.sjm.test.arithmetic

class KSequence<T>(): KParser<T>() {
    private var subparsers = mutableListOf<KParser<T>>()

    override fun match(assemblies: List<KAssembly<T>>): List<KAssembly<T>> {
        var out = assemblies
        for (p in subparsers) {
            out = p.matchAndAssemble(out)
            if (out.isEmpty()) {
                return out
            }
        }
        return out
    }

    fun add(parser: KParser<T>) = apply { subparsers.add(parser) }
}
