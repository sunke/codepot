package net.codenest.kparser.parsing

import net.codenest.kparser.lexing.KToken
import net.codenest.kparser.lexing.KToken.Companion.createQuote
import net.codenest.kparser.lexing.KToken.Companion.createSymbol
import net.codenest.kparser.lexing.KToken.Companion.createWord
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class KAssemblyTest {

    @Test
    fun testTokenAssembly() {
        var expect: List<KToken>?

        expect = listOf(createWord("Let's"), createQuote("'rock and roll'"), createSymbol("!"))
        assertTokens(expect, "Let's 'rock and roll'!")
    }

    @Test
    fun testPrintAssembly() {
        assertEquals("[]|^Congress/admitted/Colorado/in/1876.0/.",
                KTokenAssembly("Congress admitted Colorado in 1876.", "/").toString())

        assertEquals("[]|^admitted/(/colorado/,/1876.0/)",
                KTokenAssembly("admitted(colorado, 1876)", "/").toString())
    }

    private fun assertTokens(expect: List<KToken>, str: String) {
        val assembly = KTokenAssembly(str)
        val actual = mutableListOf<KToken>()
        while(assembly.hasMoreItem()) {
            actual.add(assembly.nextItem()!!)
        }

        assertEquals(expect, actual)
    }
}