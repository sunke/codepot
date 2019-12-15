package com.sjm.test.arithmetic


abstract class KAssembler<T> {
    abstract fun workOn(assembly: KAssembly<T>)
}