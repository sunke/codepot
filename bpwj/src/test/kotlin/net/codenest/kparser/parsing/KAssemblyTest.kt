package net.codenest.kparser.parsing

import net.codenest.kparser.lexing.KToken
import net.codenest.kparser.lexing.KTokenType
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class KAssemblyTest {

    @Test
    fun testTokenAssembly() {
        var expect: List<KToken>?

        expect = listOf(
                KToken("Let's"),
                KToken(KTokenType.TT_QUOTED, "'rock and roll'", 0.0),
                KToken('!'))
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