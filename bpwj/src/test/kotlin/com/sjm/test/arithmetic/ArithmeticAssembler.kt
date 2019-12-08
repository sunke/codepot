package com.sjm.test.arithmetic

import com.sjm.parse.Assembler
import com.sjm.parse.Assembly
import com.sjm.parse.tokens.Token

class DivideAssembler : Assembler() {
    /**
     * Pop two numbers from the stack and push the result of dividing the top number into the one below it.
     */
    override fun workOn(a: Assembly) {
        val d1 = a.pop() as Double
        val d2 = a.pop() as Double
        a.push(d2 / d1)
    }
}

class ExpAssembler : Assembler() {
    /**
     * Pop two numbers from the stack and push the result of exponentiation the lower number to the upper one.
     */
    override fun workOn(a: Assembly) {
        val d1 = a.pop() as Double
        val d2 = a.pop() as Double
        a.push(Math.pow(d2, d1))
    }
}

class MinusAssembler : Assembler() {
    /**
     * Pop two numbers from the stack and push the result of subtracting the top number from the one below it.
     */
    override fun workOn(a: Assembly) {
        val d1 = a.pop() as Double
        val d2 = a.pop() as Double
        a.push(d2 - d1)
    }
}

class NumAssembler : Assembler() {
    /**
     * Replace the top token in the stack with the token's Double value.
     */
    override fun workOn(a: Assembly) {
        val t = a.pop() as Token
        a.push(t.nval())
    }
}

class PlusAssembler : Assembler() {
    /**
     * Pop two numbers from the stack and push their sum.
     */
    override fun workOn(a: Assembly) {
        val d1 = a.pop() as Double
        val d2 = a.pop() as Double
        a.push(d2 + d1)
    }
}

class TimesAssembler : Assembler() {
    /**
     * Pop two numbers from the stack and push the result of multiplying the top number by the one below it.
     */
    override fun workOn(a: Assembly) {
        val d1 = a.pop() as Double
        val d2 = a.pop() as Double
        a.push(d2 * d1)
    }
}