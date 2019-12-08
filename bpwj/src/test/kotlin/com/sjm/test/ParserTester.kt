package com.sjm.test

import com.sjm.parse.Assembly
import com.sjm.parse.Parser
import com.sjm.utensil.PubliclyCloneable
import java.util.*

/**
 * This class generates random language elements for a
 * parser and tests that the parser can accept them.
 *
 * @author Steven J. Metsker
 * @version 1.0
 */
abstract class ParserTester (var p: Parser) {
    private var logTestStrings = true

    companion object {
        /**
         * Return a subset of the supplied vector of assemblies,
         * filtering for assemblies that have been completely
         * matched.
         *
         * @param input a collection of partially or completely matched assemblies
         *
         * @return a collection of completely matched assemblies
         */
        fun completeMatches(input: List<Assembly>) = input.filter{ !it.hasMoreElements() }
    }

    /*
     * Subclasses must override this, to produce an assembly
     * from the given (random) string.
     */
    protected abstract fun assembly(s: String?): Assembly

    /*
     * Generate a random language element, and return true if the parser cannot unambiguously parse it.
     */
    private fun canGenerateProblem(depth: Int): Boolean {
        val s = p.randomInput(depth, separator())
        logTestString(s)
        val a = assembly(s)
        a.setTarget(freshTarget())
        val input = Vector<Any>()
        input.addElement(a)
        val out = completeMatches(p.match(input) as Vector<Assembly>)
        if (out.size != 1) {
            logProblemFound(s, out.size)
            return true
        }
        return false
    }

    /*
     * Give subclasses a chance to provide fresh target at
     * the beginning of a parse.
     */
    protected fun freshTarget(): PubliclyCloneable? {
        return null
    }

    /*
     * This method is broken out to allow subclasses to create
     * less verbose tester, or to direct logging to somewhere
     * other than System.out.
     */
    protected fun logDepthChange(depth: Int) {
        println("Testing depth $depth...")
    }

    /*
     * This method is broken out to allow subclasses to create
     * less verbose tester, or to direct logging to somewhere
     * other than System.out.
     */
    protected fun logPassed() {
        println("No problems found.")
    }

    /*
     * This method is broken out to allow subclasses to create
     * less verbose tester, or to direct logging to somewhere
     * other than System.out.
     */
    protected fun logProblemFound(s: String?, matchSize: Int) {
        println("Problem found for string:")
        println(s)
        if (matchSize == 0) {
            println(
                    "Parser cannot match this apparently " +
                            "valid string.")
        } else {
            println(
                    "The parser found " + matchSize +
                            " ways to parse this string.")
        }
    }

    /*
     * This method is broken out to allow subclasses to create
     * less verbose tester, or to direct logging to somewhere
     * other than System.out.
     */
    protected fun logTestString(s: String) {
        if (logTestStrings) {
            println("    Testing string $s")
        }
    }

    /*
     * By default, place a blank between randomly generated
     * "words" of a language.
     */
    protected fun separator(): String {
        return " "
    }

    /**
     * Create a series of random language elements, and test
     * that the parser can unambiguously parse each one.
     */
    fun test() {
        for (depth in 2..7) {
            logDepthChange(depth)
            for (k in 0..99) {
                if (canGenerateProblem(depth)) {
                    return
                }
            }
        }
        logPassed()
    }



}