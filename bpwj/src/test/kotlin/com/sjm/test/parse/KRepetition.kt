package com.sjm.test.parse

class KRepetition<T>(private val subParser: KParser<T>, name: String = "Repetition") : KParser<T>(name) {

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