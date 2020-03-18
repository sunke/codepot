package net.codenest.kparser.lexing

import org.apache.log4j.Logger
import java.io.Reader

/**
 * A character reader that allows characters to be pushed back to the stream.
 */
class KCharReader(private val input: Reader) {

    private var log: Logger = Logger.getLogger(KCharReader::class.java.name)

    private var buf = mutableListOf<Int>()

    var row = 1


    fun read(): Int {
        val c = if (buf.isNotEmpty()) buf.removeAt(buf.size - 1) else input.read()
        if (c == '\n'.toInt()) row++
        log.debug("read: " + printChar(c))
        return c
    }

    fun unread(c: Int) {
        if (c != -1) unread(c.toChar())
    }

    fun unread(ch: Char) {
        log.debug("unread: $ch")
        buf.add(ch.toInt())
        if (ch == '\n') row--
    }

    fun close() {
        input.close()
    }

    private fun printChar(c: Int) =
            when (c) {
                -1 -> "-1"
                '\n'.toInt() -> "\\n"
                '\r'.toInt() -> "\\r"
                else -> c.toChar().toString()
            }
}