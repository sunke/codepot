package net.codenest.kparser.parsing

import net.codenest.kparser.lexing.KToken


/**
 * An assembler helps a parser build a result.
 */
abstract class KAssembler<T> {
    abstract fun workOn(assembly: KAssembly<T>)
}

abstract class KTokenAssembler: KAssembler<KToken>()

abstract class KCharAssembler: KAssembler<Char>()