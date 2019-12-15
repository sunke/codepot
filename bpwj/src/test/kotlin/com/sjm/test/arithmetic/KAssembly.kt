package com.sjm.test.arithmetic

abstract class KAssembly<T>(val delimiter: String = "") {

    var stack = mutableListOf<T>()

    // which element is next
    private var index = 0

    fun push(t: T) {
        stack.add(t)
    }

    fun pop(): T? {
        val item: T? = stack.lastOrNull()
        if (stack.isNotEmpty()) {
            stack.removeAt(stack.size - 1)
        }
        return item
    }

    /**
     * @return a string presentation of the elements of the assembly that have been consumed.
     *
     * @param delimiter the mark to show between consumed elements
     */
    abstract fun consumed(delimiter: String): String

    /**
     * @return the elements of the assembly that remain to be
     * consumed, separated by the specified delimiter.
     *
     * @param delimiter the mark to show between unconsumed elements
     */
    abstract fun remainder(delimiter: String): String

    /**
     * @return the number of elements in this assembly
     */
    abstract fun length(): Int

    /**
     * Shows the next object in the assembly, without removing it
     *
     * @return the next object
     */
    abstract fun peek(): T?

    /**
     * @return the number of elements that have been consumed.
     */
    fun elementsConsumed() = index

    /**
     * @return the number of elements that have not been consumed.
     */
    fun elementsRemaining() = length() - elementsConsumed()

    /**
     * @return true if this assembly has more elements to consume.
     */
    fun hasMoreElements() = elementsConsumed() < length()

    /**
     * @return true, if this assembly's stack is empty
     */
    fun isStackEmpty() = stack.isEmpty()

    override fun toString()= stack.toString() + consumed(delimiter) + "^" + remainder(delimiter)
}