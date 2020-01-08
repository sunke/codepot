package com.sjm.test.arithmetic

import com.sjm.parse.tokens.TokenAssembly
import com.sjm.test.ParserTester
import io.kotlintest.matchers.shouldBe

import io.kotlintest.specs.FunSpec

class KArithmeticParserTest: FunSpec() {

    init {
        test("Test operator associativity") {
            KArithmeticParser.Companion.value("7 - 3 - 1") shouldBe 3.0
        }
    }

//    @Test
//    fun testAssociativity() {
//        assertResult("7 - 3 - 1", 3.0) // minus associativity
//        assertResult("2 ^ 1 ^ 4", 2.0) // exp associativity
//    }
//
//    @Test
//    fun testIllegalExpression() {
//        assertIllegalExpression("7 / ")
//        assertIllegalExpression("7 ++ 6 ")
//        assertIllegalExpression("7 **/ 2 ")
//        assertIllegalExpression("7^*2")
//    }
//
//    @Test
//    fun testInteger() {
//        assertResult("42", 42.0)
//    }
//
//    @Test
//    fun testParentheses() {
//        assertResult("((3 * 7) + (11 * 3)) / 3", 18.0)
//    }
//
//    @Test
//    fun testPrecedence() {
//        assertResult("7 - 3 * 2 + 6", 7.0)
//        assertResult("2^1^4", 2.0)
//        assertResult("2^3^2", 512.0)
//        assertResult("1000+2*2^3^2/2", 1512.0)
//        assertResult("3*2^2*3", 36.0)
//    }
//
//    @Test
//    fun testRandomExpressions() {
//        ParserTester(KArithmeticParser.start(), false).test(::TokenAssembly)
//    }
//
//    companion object {
//        private fun assertResult(s: String, d: Double) {
//            assertEquals(d, KArithmeticParser.Companion.value(s))
//        }
//
//        private fun assertIllegalExpression(s: String) {
//            val exception = assertThrows(RuntimeException::class.java) {
//                KArithmeticParser.Companion.value(s)
//            }
//            assertEquals(KArithmeticParser.ERR_IMPROPERLY_FORMED, exception.message)
//        }
//    }
}