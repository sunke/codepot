package net.codenest.kparser.lexing


/**
  * A token represents a logical chunk of a string. For example, a typical tokenizer would break the string
  * `"1.23 <= 12.3"` into three tokens: the number 1.23, a less-than-or-equal symbol, and the number 12.3.
  * A token is a receptacle, and relies on a tokenizer to decide precisely how to divide a string into tokens.
  */
class KToken (var ttype: KTokenType, var sval: String?, var nval: Double) {

    companion object {
        val START = KToken(KTokenType.TT_START, "", 0.0)
        val END = KToken(KTokenType.TT_END, "", 0.0)
        val SKIP = KToken(KTokenType.TT_SKIP, "", 0.0)

        val NUMERIC_TOLERANCE = 1e-6;
    }

    constructor(ch: Char) : this(KTokenType.TT_SYMBOL, ch.toString(), 0.0) {}

    constructor(number: Double) : this(KTokenType.TT_NUMBER, "", number) {}

    constructor(word: String) : this(KTokenType.TT_WORD, word, 0.0) {}

    fun isNumber() = ttype === KTokenType.TT_NUMBER

    fun isQuotedString() = ttype === KTokenType.TT_QUOTED

    fun isSymbol() = ttype === KTokenType.TT_SYMBOL

    fun isWord() = ttype === KTokenType.TT_WORD

    fun value(): Any = when(ttype) {
        KTokenType.TT_NUMBER -> nval
        KTokenType.TT_END -> END
        else -> sval?.toString() ?: ttype
    }

    fun equals(other: Any?, ignoreCase: Boolean = false): Boolean {
        if (other !is KToken) return false

        if (ttype !== other.ttype) return false

        return when(ttype) {
            KTokenType.TT_NUMBER -> Math.abs(nval - other.nval) < NUMERIC_TOLERANCE
            else -> sval?.equals(other.sval, ignoreCase) ?: (other.sval === null)
        }
    }

    override fun equals(other: Any?) = equals(other, false)

    override fun toString() = when {
        ttype === KTokenType.TT_START -> "START"
        ttype === KTokenType.TT_END -> "EOF"
        ttype === KTokenType.TT_SKIP -> "SKIP"
        else -> value().toString()
    }
}

enum class KTokenType {
    TT_SKIP,
    TT_START,
    TT_END,
    TT_NUMBER,
    TT_WORD,
    TT_SYMBOL,
    TT_QUOTED
}
