package net.codenest.kparser.example.regularexpr

import com.sjm.examples.regular.*
import com.sjm.parse.Parser
import com.sjm.parse.chars.CharacterAssembly
import net.codenest.kparser.parsing.KAlternation
import net.codenest.kparser.parsing.KParser
import net.codenest.kparser.parsing.KRepetition
import net.codenest.kparser.parsing.KSequence

/**
 * This class provides a parser that recognizes regular expressions.
 * <p>
 * Regular expressions are a "metalanguage", which means they
 * form a language for describing languages. For example,
 * <code>a*</code> is a regular expression that describes a
 * simple language whose elements are strings composed of 0
 * or more <code>a's</code>. Thus the result of parsing
 * <code>a*</code> is a new parser, namely a
 * parser that will match strings of <code>a's</code>.
 * <p>
 * This class exists to show how a simple regular expression
 * parser works. It recognizes expressions according to
 * the following rules.
 *
 * ```
 *     expression    = term orTerm*;
 *     term          = factor nextFactor*;
 *     orTerm        = '|' term;
 *     factor        = phrase | phraseStar;
 *     nextFactor    = factor;
 *     phrase        = letterOrDigit | '(' expression ')';
 *     phraseStar    = phrase '*';
 *     letterOrDigit = Letter | Digit;
 * ```
 *
 * These rules recognize conventional operator precedence.
 * They also avoid the problem of left recursion, and their
 * implementation avoids problems with the infinite loop
 * inherent in the cyclic dependencies of the rules.
 *
 * @author Steven J. Metsker, Alan K. Sun
 *
 * @version 2.0
 */
class KRegularParser {
    private lateinit var expr: KSequence<Char>

    /**
     * Returns a parser that will recognize a regular expression.
     */
    fun expression(level: Int = 0): KParser<Char> {
        if (!this::expr.isInitialized) {
            expr = KSequence("Expression", level)
            expr.add(term(level + 1))
            expr.add(KRepetition<Char>(level = level+1, subParser = orTerm()))
        }
        return expr
    }

    /**
     * Returns a parser that for the grammar rule:
     * ```
     *      factor = phrase | phraseStar;
     * ```
     */
    private fun factor(level: Int = 0): KParser<Char> {
        val alt = KAlternation<Char>("Factor", level)
        alt.add(phrase(level + 1))
        alt.add(phraseStar(level + 1))
        return alt
    }

    /*
 * Returns a parser that for the grammar rule:
 *
 *    letterOrDigit = Letter | Digit;
 *
 * This parser has an assembler that will pop a
 * character and push a SpecificChar parser in its
 * place.
 */
    private fun letterOrDigit(level: Int = 0): KParser<Char> {
        val alt = KAlternation<Char>("LetterOrDigit", level)
        //a.add(Letter())
        //a.add(Digit())
        //a.setAssembler(CharAssembler())
        return alt
    }

    /**
     * Returns a parser that for the grammar rule:
     * ```
     *      nextFactor = factor;
     * ```
     */
    private fun nextFactor(level: Int = 0): KParser<Char> {
        val p = factor(level)
        //p.setAssembler(AndAssembler())
        return p
    }

    /*
 * Returns a parser that for the grammar rule:
 *
 *    orTerm = '|' term;
 *
 * This parser has an assembler that will pop two
 * parsers and push an Alternation of them.
 */
    private fun orTerm(level: Int = 0): KParser<Char> {
        val seq = KSequence<Char>("OrTerm", level)
        //seq.add(SpecificChar('|').discard())
        seq.add(term(level + 1))
        //seq.setAssembler(OrAssembler())
        return seq
    }

    /*
 * Returns a parser that for the grammar rule:
 *
 *     phrase = letterOrDigit | '(' expression ')';
 */
    private fun phrase(level: Int = 0): KParser<Char> {
        val alt = KAlternation<Char>("Phrase", level)
        alt.add(letterOrDigit(level + 1))
        val seq = KSequence<Char>(level = level + 1)
        //seq.add(SpecificChar('(').discard())
        seq.add(expression(level + 2))
        //seq.add(SpecificChar(')').discard())
        alt.add(seq)
        return alt
    }

    /*
 * Returns a parser that for the grammar rule:
 *
 *    phraseStar = phrase '*';
 *
 * This parser has an assembler that will pop a
 * parser and push a Repetition of it.
 */
    private fun phraseStar(level: Int = 0): KParser<Char> {
        val seq = KSequence<Char>("PhraseStar", level)
        seq.add(phrase(level + 1))
        //seq.add(SpecificChar('*').discard())
        //seq.setAssembler(StarAssembler())
        return seq
    }

    /**
     * Returns a parser that for the grammar rule:
     * ```
     *      term = factor nextFactor*;
     * ```
     */
    private fun term(level: Int = 0): KParser<Char> {
        val term = KSequence<Char>("Term", level)
        term.add(factor(level + 1))
        term.add(KRepetition<Char>(level = level+1, subParser = nextFactor()))
        return term
    }

    companion object {
        /**
         * Returns a parser that will recognize a regular
         * expression.
         *
         * @return a parser that will recognize a regular
         * expression
         */
        fun start(): Parser {
            return KRegularParser().expression()
        }

        /**
         * Return a parser that will match a `
         * CharacterAssembly`, according to the value of a
         * regular expression given in a string.
         *
         * For example, given the string `a*`, this
         * method will return a parser which will match any element
         * of the set `{"", "a", "aa", "aaa", ...}`.
         *
         * @return a parser that will match a `
         * CharacterAssembly`, according to the value
         * of a regular expression in the given string
         *
         * @param   String   the string to evaluate
         *
         * @exception RegularExpressionException if this parser
         * does not recognize the given string as a
         * valid expression
         */
        @Throws(RegularExpressionException::class)
        fun value(s: String?): Parser {
            val c = CharacterAssembly(s)
            val a = start().completeMatch(c)
                    ?: throw RegularExpressionException(
                            "Improperly formed regular expression")
            val p: Parser
            p = try {
                a.pop() as Parser
            } catch (e: Exception) {
                throw RegularExpressionException(
                        "Internal error in RegularParser")
            }
            return p
        }
    }
}