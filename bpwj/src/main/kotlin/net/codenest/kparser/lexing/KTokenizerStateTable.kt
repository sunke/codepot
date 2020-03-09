package net.codenest.kparser.lexing

object KTokenizerStateTable {

    private val states = Array<KTokenizerState>(256) { KSymbolState }

    init {
        for (i in 0..' '.toInt()) states[i] = KWhitespaceState
        for (i in 'a'.toInt()..'z'.toInt()) states[i] = KWordState
        for (i in 'A'.toInt()..'Z'.toInt()) states[i] = KWordState
        for (i in 0xc0..0xff) states[i] = KWordState
        for (i in '0'.toInt()..'9'.toInt()) states[i] = KNumberState
        states['-'.toInt()] = KNumberState
        states['"'.toInt()] = KQuoteState
        states['\''.toInt()] = KQuoteState
        states['/'.toInt()] = KSlashState
    }

    fun isValidChar(c: Int) = c in states.indices

    fun isWhitespace(ch: Char) = getState(ch, null) is KWhitespaceState

    fun isQuote(ch: Char) = getState(ch, null) is KQuoteState

    fun setState(ch: Char, state: KTokenizerState) {
        states[ch.toInt()] = state
    }

    fun getState(ch: Char, previous: KTokenizerState?): KTokenizerState {
        if (ch == '-' && previous is KNumberState) {
            return KSymbolState
        }
        return states[ch.toInt()]
    }
}