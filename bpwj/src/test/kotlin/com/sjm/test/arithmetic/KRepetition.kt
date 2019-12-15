package com.sjm.test.arithmetic

class KRepetition<T>(private val subparser: KParser<T>) : KParser<T>() {

    override fun match(assemblies: List<KAssembly<T>>): List<KAssembly<T>> {
        var out = assemblies.toMutableList()

        var s = assemblies
        while (!s.isEmpty()) {
            s = subparser.matchAndAssemble(s)
            out.addAll(s)
        }
        return out
    }
}