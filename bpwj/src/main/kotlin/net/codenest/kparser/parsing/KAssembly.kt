package net.codenest.kparser.parsing

import net.codenest.kparser.lexing.KToken
import net.codenest.kparser.lexing.KTokenizer
import org.apache.log4j.Logger
import java.io.Reader
import java.io.StringReader

/**
 * An assembly provides a parser with a work area.
 */
open class KAssembly<T>(private val delimiter: String = "/") {

    // store the intermediate or final parsing result
    private var resultStack = mutableListOf<Any>()

    fun push(t: Any) = apply { resultStack.add(t) }

    fun pop(): Any? = if (resultStack.isNotEmpty()) resultStack.removeAt(resultStack.size - 1) else null


    // store the input items. The items can be tokens or characters
    private var itemList = mutableListOf<T>()

    private var consumedItemPos = 0

    fun addItem(t: T) = apply { itemList.add(t) }

    fun peekItem(): T? = if (consumedItemPos < itemList.size) itemList[consumedItemPos] else null

    fun nextItem(): T? = if (consumedItemPos < itemList.size) itemList[consumedItemPos++] else null

    // return true if this assembly has more items to consume.
    fun hasMoreItem() = peekItem() != null

    // return the number of items in this assembly
    fun itemNr() = itemList.size

    // return the number of items that have been consumed.
    fun consumedItemNr() = consumedItemPos

    // return the number of items that have not been consumed.
    fun remainItemNr() = itemNr() - consumedItemNr()

    fun clone(): KAssembly<T> {
        val clone = KAssembly<T>()
        clone.resultStack = this.resultStack.toMutableList()
        clone.itemList = this.itemList.toMutableList()
        clone.consumedItemPos = this.consumedItemPos
        return clone
    }

    override fun toString() = resultStack.toString() + "|" + consumedItems() + "^" + remainItems()

    private fun consumedItems(): String = itemList.subList(0, consumedItemNr()).joinToString(separator = delimiter)

    private fun remainItems(): String = itemList.subList(consumedItemNr(), itemNr()).joinToString(separator = delimiter)
}

class KTokenAssembly(private val reader: Reader, delimiter: String = "/"): KAssembly<KToken>(delimiter) {
    private var log: Logger = Logger.getLogger(KTokenAssembly::class.java.name)

    constructor(str: String, delimiter: String = "/"): this(StringReader(str), delimiter)

    init {
        val tokenizer = KTokenizer(reader)
        var next = tokenizer.nextToken()
        while (next != KToken.END) {
            if (next != KToken.START && next != KToken.SKIP) {
                addItem(next)
                log.debug("add token: ${next.ttype} $next")
            }
            next = tokenizer.nextToken()
        }
        log.debug("$this")
    }
}

class KCharAsseembly: KAssembly<Char>()