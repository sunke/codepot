package com.sjm.test.arithmetic

import com.sjm.parse.tokens.Token
import com.sjm.test.parsing.KAssembly
import com.sjm.test.parsing.KTokenAssembler
import kotlin.math.pow

class KDivideAssembler : KTokenAssembler() {
    /**
     * Pop two numbers from the stack and push the result of dividing the top number into the one below it.
     */
    override fun workOn(assembly: KAssembly<Token>) {
        val d1 = assembly.pop() as Double
        val d2: Double = assembly.pop() as Double
        assembly.push(d2 / d1)
    }
}

class KExpAssembler : KTokenAssembler() {
    /**
     * Pop two numbers from the stack and push the result of exponentiation the lower number to the upper one.
     */
    override fun workOn(assembly: KAssembly<Token>) {
        val d1 = assembly.pop() as Double
        val d2 = assembly.pop() as Double
        assembly.push(d2.pow(d1))
    }
}

class KMinusAssembler : KTokenAssembler()  {
    /**
     * Pop two numbers from the stack and push the result of subtracting the top number from the one below it.
     */
    override fun workOn(assembly: KAssembly<Token>) {
        val d1 = assembly.pop() as Double
        val d2 = assembly.pop() as Double
        assembly.push(d2 - d1)
    }
}

class KNumAssembler : KTokenAssembler() {
    /**
     * Replace the top token in the stack with the token's Double value.
     */
    override fun workOn(assembly: KAssembly<Token>) {
        val t = assembly.pop() as Token
        assembly.push(t.nval())
    }
}

class KPlusAssembler : KTokenAssembler() {
    /**
     * Pop two numbers from the stack and push their sum.
     */
    override fun workOn(assembly: KAssembly<Token>) {
        val d1 = assembly.pop() as Double
        val d2 = assembly.pop() as Double
        assembly.push(d2 + d1)
    }
}

class KTimesAssembler : KTokenAssembler() {
    /**
     * Pop two numbers from the stack and push the result of multiplying the top number by the one below it.
     */
    override fun workOn(assembly: KAssembly<Token>) {
        val d1 = assembly.pop() as Double
        val d2 = assembly.pop() as Double
        assembly.push(d2 * d1)
    }
}