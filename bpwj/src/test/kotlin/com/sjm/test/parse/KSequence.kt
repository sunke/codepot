package com.sjm.test.parse

class KSequence<T>(name: String = "Sequence"): KParser<T>(name) {

    private val subParsers = mutableListOf<KParser<T>>()

    fun add(parser: KParser<T>) = apply { subParsers.add(parser) }

    override fun match(assemblies: List<KAssembly<T>>): List<KAssembly<T>> {
        //subParsers.fold(assemblies) { ays, p -> p.matchAndAssemble(ays) }
        var out = assemblies
        for (p in subParsers) {
            out = p.matchAndAssemble(out)
            if (out.isEmpty()) {
                return out
            }
        }
        return out
    }
}
