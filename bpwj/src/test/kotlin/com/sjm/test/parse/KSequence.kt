package com.sjm.test.parse

class KSequence<T>(name: String = "Sequence"): KParser<T>(name) {

    private val subParsers = mutableListOf<KParser<T>>()

    fun add(parser: KParser<T>) = apply { subParsers.add(parser) }

    override fun match(assemblies: List<KAssembly<T>>): List<KAssembly<T>> {
        //subParsers.fold(assemblies) { ays, p -> p.matchAndAssemble(ays) }
        var ays = assemblies
        for (p in subParsers) {
            ays = p.matchAndAssemble(ays)
            if (ays.isEmpty()) {
                return ays
            }
        }
        return ays
    }
}
