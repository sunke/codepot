package com.sjm.test.arithmetic

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.sjm.parse.tokens.Token
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class KRepetitionTest {
    val subparser = object : KParser<Token>() {
        override fun match(assemblies: List<KAssembly<Token>>): List<KAssembly<Token>> {
            return emptyList()
        }
    }

    val parser = KRepetition<Token>("", subparser)


    @Test
    fun testMatchEmptyList() {
        val subparser = mock(emptyList())
        val parser = KRepetition<Token>("", subparser)

        assertEquals(emptyList(), parser.match(emptyList()))
    }

    //@Test
    fun testMatch() {
        val ays = listOf(KTokenAssembly(), KTokenAssembly())

        val subparser = mock(ays)
        val parser = KRepetition<Token>("", subparser)

        assertEquals(ays, parser.match(ays))
    }

    fun mock(ays: List<KAssembly<Token>>): KParser<Token> {
        return  object : KParser<Token>() {
            override fun match(assemblies: List<KAssembly<Token>>): List<KAssembly<Token>> {
                return assemblies.dropLast(1)
            }
        }
    }
}

