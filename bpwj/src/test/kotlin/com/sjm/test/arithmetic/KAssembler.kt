package com.sjm.test.arithmetic


/**
 * An assembler helps a parser build a result.
 */
abstract class KAssembler<T> {
    abstract fun workOn(assembly: KAssembly<T>)
}