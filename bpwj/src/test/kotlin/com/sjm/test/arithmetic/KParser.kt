package com.sjm.test.arithmetic


/**
 * A parser is an object that recognizes a language.
 */
abstract class KParser<T> {
    private var assembler: KAssembler<T>? = null

    fun completeMatch(assembly: KAssembly<T>): KAssembly<T>? {
        var best = bestMatch(assembly);
        return if (best != null && !best.hasMoreElements()) best else null
    }

    fun bestMatch(assembly: KAssembly<T>): KAssembly<T>? {
        return matchAndAssemble(listOf(assembly)).sortedBy { it.elementsConsumed() }[0]
    }

    fun matchAndAssemble(assemblies: List<KAssembly<T>>): List<KAssembly<T>> {
        return match(assemblies).apply { forEach { assembler?.workOn(it) } }
    }

    abstract fun match(assemblies: List<KAssembly<T>>): List<KAssembly<T>>
}