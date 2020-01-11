package com.sjm.test.parse


/**
 * A parser is an object that recognizes a language.
 */
abstract class KParser<T>(val name: String = "") {
    var assembler: KAssembler<T>? = null

    fun setAssembler(assembler: KAssembler<T>): KParser<T> {
        this.assembler = assembler
        return this
    }

    fun completeMatch(assembly: KAssembly<T>): KAssembly<T>? {
        var best = bestMatch(assembly);
        return if (best != null && !best.hasMoreItem()) best else null
    }

    fun bestMatch(assembly: KAssembly<T>): KAssembly<T>? {
        return matchAndAssemble(listOf(assembly)).minBy { it.remainItemNr() }
    }

    fun matchAndAssemble(assemblies: List<KAssembly<T>>): List<KAssembly<T>> {
        println(name)
        return match(assemblies).apply { forEach { assembler?.workOn(it) } }
    }

    /**
     * Given a list of assemblies, this method matches this parser against all of them,
     * and returns a new list of the assemblies that result from the matches.
     *
     * For example, consider matching the regular expression <code>a*</code> against
     * the string <code>"aaab"</code>. The initial set of states is <code>{^aaab}</code>,
     * where the ^ indicates how far along the assembly is. When <code>a*</code> matches
     * against this initial state, it creates a new set <code>{^aaab, a^aab, aa^ab,
     * aaa^b}</code>.
     *
     * @param assemblies a list of assemblies to match against
     *
     * @return a list of assemblies that result from matching against a beginning list of assemblies
     */
    abstract fun match(assemblies: List<KAssembly<T>>): List<KAssembly<T>>
}