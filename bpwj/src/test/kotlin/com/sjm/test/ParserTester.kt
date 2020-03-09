package com.sjm.test

import com.sjm.parse.Assembly
import com.sjm.parse.Parser
import java.util.*

/**
 * This class generates random language elements for a
 * parser and tests that the parser can accept them.
 */
class ParserTester(var parser: Parser, private val showLog: Boolean = true) {

    companion object {
        fun completeMatches(input: List<Assembly>) = input.filter { !it.hasMoreElements() }
    }

    private fun print(s: String) {
        if (showLog) println(s)
    }

    /**
     * Create a series of random language elements, and test that the parser can unambiguously parse each one.
     */
    fun test(assembly: (String) -> Assembly) {
        for (depth in 2..7) {
            print("Testing depth $depth ...")
            repeat(100) {
                val s = parser.randomInput(depth, " ")
                print("\tTesting string $s")
                val out = completeMatches(parser.match(listOf(assembly(s))) as List<Assembly>)
                if (out.isEmpty()) {
                    throw Exception("The parser cannot match this string: $s")
                } else if (out.size > 1) {
                    throw Exception("The parser found ${out.size} ways to parse this string: $s")
                }
            }
        }
    }
}