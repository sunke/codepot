package com.sjm.test.arithmetic

class KCharacterAssembly() : KAssembly<Char>() {
    private var string: String = ""

    constructor(string: String) : this() {
        this.string = string
    }

    override fun consumed(delimiter: String): String {
        val s = string.substring(0, elementsConsumed())
        return if (delimiter == "") s else sequenceOf(s).joinToString(separator = delimiter)

    }

    override fun remainder(delimiter: String): String {
        val s = string.substring(elementsConsumed() + 1)
        return if (delimiter == "") s else sequenceOf(s).joinToString(separator = delimiter)
    }

    override fun length() = string.length

    override fun peek(): Char? = if (elementsConsumed() >= length() - 1) null else string[elementsConsumed() + 1]
}