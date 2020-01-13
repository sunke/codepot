package com.sjm.test.parse

val EOF = KToken(KTokenType.TT_EOF, "", 0.0)

/**
  * A token represents a logical chunk of a string. For example, a typical tokenizer would break the string
  * `"1.23 <= 12.3"` into three tokens: the number 1.23, a less-than-or-equal symbol, and the number 12.3.
  * A token is a receptacle, and relies on a tokenizer to decide precisely how to divide a string into tokens.
  */
class KToken (var ttype: KTokenType, var sval: String?, var nval: Double) {

    constructor(c: Char) : this(KTokenType.TT_SYMBOL, c.toString(), 0.0) {}

    constructor(nval: Double) : this(KTokenType.TT_NUMBER, "", nval) {}

    constructor(sval: String) : this(KTokenType.TT_WORD, sval, 0.0) {}

    val isNumber: Boolean
        get() = ttype === KTokenType.TT_NUMBER

    val isQuotedString: Boolean
        get() = ttype === KTokenType.TT_QUOTED

    val isSymbol: Boolean
        get() = ttype === KTokenType.TT_SYMBOL

    val isWord: Boolean
        get() = ttype === KTokenType.TT_WORD

    override fun equals(o: Any?): Boolean {
        if (o !is KToken) return false
        if (ttype !== o.ttype) {
            return false
        }
        if (ttype === KTokenType.TT_NUMBER) {
            return nval == o.nval
        }
        return if (sval == null || o.sval == null) {
            false
        } else sval == o.sval
    }

    fun equalsIgnoreCase(o: Any?): Boolean {
        if (o !is KToken) return false
        if (ttype !== o.ttype) {
            return false
        }
        if (ttype === KTokenType.TT_NUMBER) {
            return nval == o.nval
        }
        return if (sval == null || o.sval == null) {
            false
        } else sval.equals(o.sval, ignoreCase = true)
    }

    fun value() = when(ttype) {
        KTokenType.TT_NUMBER -> nval
        KTokenType.TT_EOF -> EOF
        else -> sval?.toString() ?: ttype
    }


    override fun toString() = if (ttype === KTokenType.TT_EOF) "EOF" else value().toString()
}

enum class KTokenType {
    TT_EOF,
    TT_NUMBER,
    TT_WORD,
    TT_SYMBOL,
    TT_QUOTED
}
