package com.sjm.test.parse

import com.sjm.parse.tokens.Token


/**
 * An assembler helps a parser build a result.
 */
abstract class KAssembler<T> {
    abstract fun workOn(assembly: KAssembly<T>)
}

abstract class KTokenAssembler: KAssembler<Token>()

abstract class KCharAssembler: KAssembler<Char>()