package net.codenest.kparser.lexing


/**
 * ```
 * The idea of a symbol is a character that stands on its own, such as an ampersand or a parenthesis. For example,
 * when tokenizing the expression (isReady)&(isWilling), a typical tokenizer would return 7 tokens, including one
 * for each parenthesis and one for the ampersand. Thus a series of symbols such as )&( becomes three tokens,
 * while a series of letters such as isReady becomes a single word token.
 *
 * Multi-character symbols are an exception to the rule that a symbol is a standalone character.  For example, a
 * tokenizer may want less-than-or-equals to tokenize as a single token. This class provides a method for
 * establishing which multi-character symbols an object of this class should treat as single symbols. This allows,
 * for example, <code>"cat <= dog"</code> to tokenize as three tokens, rather than splitting the less-than and
 * equals symbols into separate tokens.
 *
 * By default, this state recognizes the following multi-character symbols: !=, :-, <=, >=
 * ```
 */
object KSymbolState : KTokenizerState {
    private var root = KSymbolNode(0.toChar())

    init {
        root.addSymbol("!=")
        root.addSymbol(">=")
        root.addSymbol("<=")
    }

    override fun nextToken(ch: Char, reader: CharReader): KToken {
        var symbol = root.findSymbol(ch.toChar(), reader)
        if (symbol.isEmpty()) {
            symbol = ch.toString()
        }
        return KToken(KTokenType.TT_SYMBOL, symbol, 0.0)
    }

    class KSymbolNode(private val schar: Char) {
        private val children = mutableSetOf<KSymbolNode>()

        fun findSymbol(ch: Char, reader: CharReader): String {
            if (ch.toInt() < 0) return ""
            val child = findChild(ch)

            return if (child == null) {
                //TODO ??
                //tokenizer.reader.unread(ch)
                ""
            } else {
                  ch + child.findSymbol(reader.read().toChar(), reader)
            }
        }

        fun addSymbol(s: String) {
            if (s.isNotEmpty()) {
                addChild(s[0]).addSymbol(s.substring(1))
            }
        }

        private fun addChild(c: Char): KSymbolNode {
            val child = findChild(c) ?: KSymbolNode(c)
            children.add(child)
            return child
        }

        private fun findChild(c: Char): KSymbolNode? {
            return children.firstOrNull { it.schar == c }
        }
    }
}