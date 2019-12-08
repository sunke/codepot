package com.sjm.test.arithmetic

import com.sjm.parse.Alternation
import com.sjm.parse.Parser
import com.sjm.parse.Repetition
import com.sjm.parse.Sequence
import com.sjm.parse.tokens.Num
import com.sjm.parse.tokens.Symbol
import com.sjm.parse.tokens.TokenAssembly

/**
 * This class provides a parser that recognizes arithmetic expressions. This class includes the method
 * `value`, which is a "facade" that provides an example and makes the parser easy to use. For example,
 *
 * ```
 * System.out.println(ArithmeticParser.value("(5 + 4) * 3 ^ 2 - 81"));
 * ```
 * This prints out `0.0`.
 *
 * This class exists to show how a simple arithmetic parser works. It recognizes expressions according to
 * the following rules:
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
 * @version 2.0
 */
class ArithmeticParser {
    private var expression: Sequence? = null
    private var factor: Alternation? = null

    companion object {
        const val ERR_IMPROPERLY_FORMED = "Improperly formed arithmetic expression"
        private const val ERR_INTERNAL = "Internal error in ArithmeticParser"

        fun start(): Parser = ArithmeticParser().expression()

        /**
         * Return the value of an arithmetic expression given in a string. This method is a facade, which provides an
         * example of how to use the parser.
         *
         * @param s the string to evaluate.
         *
         * @return the value of an arithmetic expression given in a string
         */
        fun value(s: String): Double {
            val x = start().completeMatch(TokenAssembly(s))
                    ?: throw RuntimeException(ERR_IMPROPERLY_FORMED)
            return x.pop() as? Double ?: throw RuntimeException(ERR_INTERNAL)
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
    fun expression(): Parser {
        // This use of a static variable avoids the infinite recursion inherent in the grammar.
        if (expression == null) {
            expression = Sequence("expression")
            expression!!.add(term())
            val a = Alternation()
            a.add(plusTerm())
            a.add(minusTerm())
            expression!!.add(Repetition(a))
        }
        return expression!!
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
    private fun divideFactor(): Parser {
        val s = Sequence()
        s.add(Symbol('/').discard())
        s.add(factor())
        s.setAssembler(DivideAssembler())
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
    private fun expFactor(): Parser {
        val s = Sequence()
        s.add(Symbol('^').discard())
        s.add(factor())
        s.setAssembler(ExpAssembler())
        return s
    }


    /**
     * Returns a parser that for the grammar rule:
     *
     * ```
     *     factor = phrase expFactor | phrase;
     * ```
     */
    private fun factor(): Parser {
        /*
         * This use of a static variable avoids the infinite recursion inherent in the grammar; factor depends
         * on expFactor, and expFactor depends on factor.
         */
        if (factor == null) {
            factor = Alternation("factor")
            val s = Sequence()
            s.add(phrase())
            s.add(expFactor())
            factor!!.add(s)
            factor!!.add(phrase())
        }
        return factor!!
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
    private fun minusTerm(): Parser {
        val s = Sequence()
        s.add(Symbol('-').discard())
        s.add(term())
        s.setAssembler(MinusAssembler())
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
    private fun phrase(): Parser {
        val phrase = Alternation("phrase")
        val s = Sequence()
        s.add(Symbol('(').discard())
        s.add(expression())
        s.add(Symbol(')').discard())
        phrase.add(s)
        phrase.add(Num().setAssembler(NumAssembler()))
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
    private fun plusTerm(): Parser {
        val s = Sequence()
        s.add(Symbol('+').discard())
        s.add(term())
        s.setAssembler(PlusAssembler())
        return s
    }

    /**
     * Returns a parser that for the grammar rule:
     * ```
     *
     *    term = factor (timesFactor | divideFactor)*;
     * ```
     */
    private fun term(): Parser {
        val s = Sequence("term")
        s.add(factor())
        val a = Alternation()
        a.add(timesFactor())
        a.add(divideFactor())
        s.add(Repetition(a))
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
    private fun timesFactor(): Parser {
        val s = Sequence()
        s.add(Symbol('*').discard())
        s.add(factor())
        s.setAssembler(TimesAssembler())
        return s
    }
}