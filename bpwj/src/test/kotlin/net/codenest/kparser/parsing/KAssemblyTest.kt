package net.codenest.kparser.parsing

import net.codenest.kparser.lexing.KToken
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class KAssemblyTest {

    @Test
    fun testTokenAssembly() {
        var expect: List<KToken>?

        expect = listOf(
                KToken.createWord("Let's"),
                KToken.createQuote("'rock and roll'"),
                KToken.createSymbol("!"))
        assertTokens(expect, "Let's 'rock and roll'!")
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