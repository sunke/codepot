package net.codenest.kparser.lexing

import org.apache.log4j.Logger
import java.io.Reader
import java.lang.System.arraycopy

/**
 * A character reader that allows characters to be pushed back to the stream.
 */
class CharReader(private val input: Reader, private val bufferSize: Int = 4) {
    init {
        require(this.bufferSize > 0) { "Buffer size should larger than zero." }
    }

    private var log: Logger = Logger.getLogger(CharReader::class.java.name)

    private var buf =  Array(bufferSize) { 0.toChar() }
    private var pos = bufferSize // store chars in buf[pos .. bufferSize)

    fun read(): Int {
        val c = if (pos < bufferSize) buf[pos++].toInt() else input.read()
        log.debug("read: " + if (c == -1) "-1" else c.toChar() + " -> $this")
        return c
    }

    fun unread(c: Int) {
        if (c != -1) unread(c.toChar())
    }

    fun unread(ch: Char) {
        log.debug("unread: $ch -> $this")
        require(pos > 0) { "CharReader buffer overflow." }
        buf[--pos] = ch
    }

    fun unread(cbuf: CharArray) {
        unread(cbuf, 0, cbuf.size)
    }

    fun unread(cbuf: CharArray, off: Int, len: Int) {
        require(value = len <= pos) { "CharReader buffer overflow." }
        pos -= len
        arraycopy(cbuf, off, buf, pos, len)
    }

    fun close() {
        input.close()
    }

    override fun toString(): String {
        return buf.slice(pos until bufferSize).joinToString(",")
    }
}