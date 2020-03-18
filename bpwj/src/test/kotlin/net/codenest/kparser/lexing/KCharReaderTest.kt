package net.codenest.kparser.lexing

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.StringReader


class KCharReaderTest {

    @Test
    fun testReadNewline() {
        var str = "a\nb\r\nc\nd"
        var reader = KCharReader(StringReader(str))

        assertEquals('a', reader.read().toChar())
        assertEquals(1, reader.row)

        assertEquals('\n', reader.read().toChar())

        assertEquals('b', reader.read().toChar())
        assertEquals(2, reader.row)

        assertEquals('\r', reader.read().toChar())
        assertEquals('\n', reader.read().toChar())

        assertEquals('c', reader.read().toChar())
        assertEquals(3, reader.row)

        assertEquals('\n', reader.read().toChar())

        assertEquals('d', reader.read().toChar())

        assertEquals(-1, reader.read())
    }

    @Test
    fun testSpecialCases() {
        var str = "a\n\nb\r\r\nc"
        var reader = KCharReader(StringReader(str))

        assertEquals('a', reader.read().toChar())
        assertEquals(1, reader.row)

        assertEquals('\n', reader.read().toChar())
        assertEquals('\n', reader.read().toChar())

        assertEquals('b', reader.read().toChar())
        assertEquals(3, reader.row)

        assertEquals('\r', reader.read().toChar())
        assertEquals('\r', reader.read().toChar())
        assertEquals('\n', reader.read().toChar())

        assertEquals('c', reader.read().toChar())
        assertEquals(4, reader.row)

        assertEquals(-1, reader.read())
    }
}