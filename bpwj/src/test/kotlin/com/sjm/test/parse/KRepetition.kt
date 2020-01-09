package com.sjm.test.parse

class KRepetition<T>(private val subParser: KParser<T>) : KParser<T>(name = "Repetition") {

    override fun match(assemblies: List<KAssembly<T>>): List<KAssembly<T>> {
        var out = assemblies.toMutableList()

        var ays = assemblies
        while (ays.isNotEmpty()) {
            ays = subParser.matchAndAssemble(ays)
            out.addAll(ays)
        }
        return out
    }
}