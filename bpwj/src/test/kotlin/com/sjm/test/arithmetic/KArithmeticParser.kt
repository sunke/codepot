package com.sjm.test.arithmetic

import com.sjm.examples.arithmetic.ArithmeticParser
import com.sjm.parse.Parser
import com.sjm.parse.tokens.Token

/**
 * This class provides a parser that recognizes arithmetic expressions. It includes the method `value`,
 * which is a "facade" that makes the parser easy to use. For example,
 *
 * ```
 * System.out.println(ArithmeticParser.value("(5 + 4) * 3 ^ 2 - 81"));
 * ```
 * This prints out `0.0`.
 *
 * This parser recognizes expressions according to the following rules:
 *
 * ```
 * expression    = term (plusTerm | minusTerm)*;
 * term          = factor (timesFactor | divideFactor)*;
 * plusTerm      = '+' term;
 * minusTerm     = '-' term;
 * factor        = phrase expFactor | phrase;
 * timesFactor   = '*' factor;
 * divideFactor  = '/' factor;
 * expFactor     = '^' factor;
 * phrase        = '(' expression ')' | Num;
 * ```
 *
 * These rules recognize conventional operator precedence and  associativity. They also avoid the problem of left
 * recursion, and their implementation avoids problems with the infinite loop inherent in the cyclic dependencies of
 * the rules. In other words, the rules may look simple, but their structure is subtle.
 *
 * @author Steven J. Metsker, Alan K. Sun
 *
 * @version 2.0
 */
class KArithmeticParser {

    private lateinit var exp: KSequence<Token>
    private lateinit var fac: KAlternation<Token>

    companion object {
        const val ERR_IMPROPERLY_FORMED = "Improperly formed arithmetic expression"
        const val ERR_INTERNAL_ERROR = "Internal error in ArithmeticParser"

        fun start(): KParser<Token> = KArithmeticParser().expression()

        /**
         * Return the value of an arithmetic expression given in a string.
         */
        fun value(s: String): Double {
            val x = start().completeMatch(KTokenAssembly(s))
                    ?: throw RuntimeException(ERR_IMPROPERLY_FORMED)
            return x.pop() as? Double ?: throw RuntimeException(ERR_INTERNAL_ERROR)
        }
    }

    /**
     * Returns a parser that will recognize an arithmetic expression.
     *
     * ```
     *  expression = term (plusTerm | minusTerm)*
     * ```
     *
     * @return a parser that will recognize an arithmetic expression
     */
    fun expression(): KParser<Token> {
        if (!this::exp.isInitialized) {
            exp = KSequence<Token>("expression")
            exp.add(term())
            val a = KAlternation<Token>()
            a.add(plusTerm())
            a.add(minusTerm())
            exp.add(KRepetition<Token>(a))
        }
        return exp
    }

    /**
     * Returns a parser that for the grammar rule:
     *
     * ```
     *     divideFactor = '/' factor;
     * ```
     *
     * This parser has an assembler that will pop two numbers from the stack and push their quotient.
     */
    private fun divideFactor(): KParser<Token> {
        val s = KSequence<Token>()
        s.add(KSymbol('/').discard())
        s.add(factor())
        s.setAssembler(KDivideAssembler())
        return s
    }

    /**
     * Returns a parser that for the grammar rule:
     *
     *  ```
     *     expFactor = '^' factor;
     *  ```
     *
     * This parser has an assembler that will pop two numbers from the stack and push the result of
     * exponentiation the lower number to the upper one.
     */
    private fun expFactor(): KParser<Token> {
        val s = KSequence<Token>()
        s.add(KSymbol('^').discard())
        s.add(factor())
        s.setAssembler(KExpAssembler())
        return s
    }


    /**
     * Returns a parser that for the grammar rule:
     *
     * ```
     *     factor = phrase expFactor | phrase;
     * ```
     */
    private fun factor(): KParser<Token> {
        /*
         * This use of a static variable avoids the infinite recursion inherent in the grammar; factor depends
         * on expFactor, and expFactor depends on factor.
         */
        if(!this::fac.isInitialized) {
            fac = KAlternation<Token>()
            val s = KSequence<Token>()
            s.add(phrase())
            s.add(expFactor())
            fac.add(s)
            fac.add(phrase())
        }
        return fac
    }

    /**
     * Returns a parser that for the grammar rule:
     *
     * ```
     *     minusTerm = '-' term;
     * ```
     *
     * This parser has an assembler that will pop two numbers from the stack and push their difference.
     */
    private fun minusTerm(): KParser<Token> {
        val s = KSequence<Token>()
        s.add(KSymbol('-').discard())
        s.add(term())
        s.setAssembler(KMinusAssembler())
        return s
    }

    /**
     * Returns a parser that for the grammar rule:
     * ```
     *
     *    phrase = '(' expression ')' | Num;
     *
     * ```
     * This parser adds an assembler to Num, that will
     * replace the top token in the stack with the token's
     * Double value.
     */
    private fun phrase(): KParser<Token> {
        val phrase = KAlternation<Token>()
        val s = KSequence<Token>()
        s.add(KSymbol('(').discard())
        s.add(expression())
        s.add(KSymbol(')').discard())
        phrase.add(s)
        phrase.add(KNum().setAssembler(KNumAssembler()))
        return phrase
    }

    /**
     * Returns a parser that for the grammar rule:
     * ```
     *
     *     plusTerm = '+' term;
     *
     * ```
     * This parser has an assembler that will pop two numbers from the stack and push their sum.
     */
    private fun plusTerm(): KParser<Token> {
        val s = KSequence<Token>()
        s.add(KSymbol('+').discard())
        s.add(term())
        s.setAssembler(KPlusAssembler())
        return s
    }

    /**
     * Returns a parser that for the grammar rule:
     * ```
     *
     *    term = factor (timesFactor | divideFactor)*;
     * ```
     */
    private fun term(): KParser<Token> {
        val s = KSequence<Token>()
        s.add(factor())
        val a = KAlternation<Token>()
        a.add(timesFactor())
        a.add(divideFactor())
        s.add(KRepetition<Token>(a))
        return s
    }

    /**
     * Returns a parser that for the grammar rule:
     * ```
     *
     *     timesFactor = '*' factor;
     *
     * ```
     * This parser has an assembler that will pop two numbers from the stack and push their product.
     */
    private fun timesFactor(): KParser<Token> {
        val s = KSequence<Token>()
        s.add(KSymbol('*').discard())
        s.add(factor())
        s.assembler = KTimesAssembler()
        return s
    }
}