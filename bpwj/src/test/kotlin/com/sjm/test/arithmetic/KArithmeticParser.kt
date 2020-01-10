package com.sjm.test.arithmetic

import com.sjm.parse.tokens.Token
import com.sjm.test.parse.*

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

const val ERR_IMPROPERLY_FORMED = "Improperly formed arithmetic expression"
const val ERR_INTERNAL_ERROR = "Internal error in ArithmeticParser"

fun calculate(exp: String): Double {
    val parser = KArithmeticParser().expression()
    val value = parser.completeMatch(KTokenAssembly(exp)) ?: throw RuntimeException(ERR_IMPROPERLY_FORMED)
    return value.pop() as? Double ?: throw RuntimeException(ERR_INTERNAL_ERROR)
}

class KArithmeticParser {

    private lateinit var expr: KSequence<Token>
    private lateinit var fact: KAlternation<Token>

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
        if (!this::expr.isInitialized) {
            expr = KSequence("Expression")
            expr.add(term())
            val alt = KAlternation<Token>("Expression-Alternation")
            alt.add(plusTerm())
            alt.add(minusTerm())
            expr.add(KRepetition(alt, "Expression-Repetition"))
        }
        return expr
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
        val div = KSequence<Token>("Division")
        div.add(KSymbol('/').discard())
        div.add(factor())
        div.setAssembler(KDivideAssembler())
        return div
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
        val exp = KSequence<Token>("Exponential")
        exp.add(KSymbol('^').discard())
        exp.add(factor())
        exp.setAssembler(KExpAssembler())
        return exp
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
        if(!this::fact.isInitialized) {
            fact = KAlternation("Factor")
            val seq = KSequence<Token>("Factor-Sequence")
            seq.add(phrase())
            seq.add(expFactor())
            fact.add(seq)
            fact.add(phrase())
        }
        return fact
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
        val minus = KSequence<Token>("Minus")
        minus.add(KSymbol('-').discard())
        minus.add(term())
        minus.setAssembler(KMinusAssembler())
        return minus
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
        val phrase = KAlternation<Token>("Phrase")
        val seq = KSequence<Token>()
        seq.add(KSymbol('(').discard())
        seq.add(expression())
        seq.add(KSymbol(')').discard())
        phrase.add(seq)
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
        val plus = KSequence<Token>("Plus")
        plus.add(KSymbol('+').discard())
        plus.add(term())
        plus.setAssembler(KPlusAssembler())
        return plus
    }

    /**
     * Returns a parser that for the grammar rule:
     * ```
     *
     *    term = factor (timesFactor | divideFactor)*;
     * ```
     */
    private fun term(): KParser<Token> {
        val term = KSequence<Token>("Term")
        term.add(factor())
        val alt = KAlternation<Token>()
        alt.add(timesFactor())
        alt.add(divideFactor())
        term.add(KRepetition(alt))
        return term
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
        val time = KSequence<Token>("Times")
        time.add(KSymbol('*').discard())
        time.add(factor())
        time.setAssembler(KTimesAssembler())
        return time
    }
}