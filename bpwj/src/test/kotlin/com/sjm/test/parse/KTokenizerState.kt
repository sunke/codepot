package com.sjm.test.parse

import com.sjm.parse.tokens.Token
import com.sjm.parse.tokens.Tokenizer
import com.sjm.parse.tokens.TokenizerState
import java.io.IOException
import java.io.PushbackReader

/**
 * A tokenizerState returns a token, given a reader, an initial character read from the reader, and a tokenizer
 * that is conducting an overall tokenization of the reader. The tokenizer will typically have a character state
 * table that decides which state to use, depending on an initial character. If a single character is insufficient,
 * a state such as `SlashState` will read a second character, and may delegate to another state,
 * such as `SlashStarState`. This prospect of delegation is the reason that the `nextToken()`
 * method has a tokenizer argument.
 *
 * @author Steven J. Metsker, Alan K. Sun
 */
abstract class KTokenizerState {
    abstract fun nextToken(r: PushbackReader, cin: Int, t: KTokenizer): KToken
}

class KNumberState: KTokenizerState() {
    override fun nextToken(r: PushbackReader, cin: Int, t: KTokenizer): KToken {
        return KToken(KTokenType.TT_NUMBER, "", 0.0)
    }
}

class KQuoteState: KTokenizerState() {
    override fun nextToken(r: PushbackReader, cin: Int, t: KTokenizer): KToken {
        return KToken(KTokenType.TT_NUMBER, "", 0.0)
    }
}

class KSymbolState: KTokenizerState() {
    override fun nextToken(r: PushbackReader, cin: Int, t: KTokenizer): KToken {
        return KToken(KTokenType.TT_NUMBER, "", 0.0)
    }
}

class KWhitespaceState: KTokenizerState() {
    override fun nextToken(r: PushbackReader, cin: Int, t: KTokenizer): KToken {
        return KToken(KTokenType.TT_NUMBER, "", 0.0)
    }
}

class KSlashState: KTokenizerState() {
    override fun nextToken(r: PushbackReader, cin: Int, t: KTokenizer): KToken {
        return KToken(KTokenType.TT_NUMBER, "", 0.0)
    }
}

class KWordState: KTokenizerState() {
    override fun nextToken(r: PushbackReader, cin: Int, t: KTokenizer): KToken {
        return KToken(KTokenType.TT_NUMBER, "", 0.0)
    }
}


class KNumberState2: KTokenizerState() {
    private var c = 0
    private var value = 0.0
    private var absorbedLeadingMinus = false
    private var absorbedDot = false
    private var gotAdigit = false

    override fun nextToken(r: PushbackReader, cin: Int, t: KTokenizer): KToken {
        c = cin
        value = 0.0
        absorbedLeadingMinus = false
        absorbedDot = false
        gotAdigit = false

        if (c == '-'.toInt()) {
            c = r.read()
            absorbedLeadingMinus = true
        }
        value = absorbDigits(r, false)

        if (c == '.'.toInt()) {
            c = r.read()
            absorbedDot = true
            value += absorbDigits(r, true)
        }

        r.unread(c)
        return value(r, t)
    }

    private fun value(r: PushbackReader, t: KTokenizer): KToken {
        if (!gotAdigit) {
            if (absorbedLeadingMinus && absorbedDot) {
                r.unread('.'.toInt())
                return t.symbolState.nextToken(r, '-'.toInt(), t)
            }
            if (absorbedLeadingMinus) {
                return t.symbolState.nextToken(r, '-'.toInt(), t)
            }
            if (absorbedDot) {
                return t.symbolState.nextToken(r, '.'.toInt(), t)
            }
        }
        if (absorbedLeadingMinus) {
            value = - value
        }
        return KToken(KTokenType.TT_NUMBER, "", value)
    }



    // Convert a stream of digits into a number, making this number a fraction if the boolean parameter is true.
    private fun absorbDigits(r: PushbackReader, fraction: Boolean): Double {
        var divideBy = 1
        var v = 0.0
        while (isDigit()) {
            gotAdigit = true
            v = v * 10 + (c - '0'.toInt())
            c = r.read()
            if (fraction) {
                divideBy *= 10
            }
        }
        if (fraction) {
            v = v / divideBy
        }
        return v
    }

    private fun isDigit() = '0'.toInt() <= c && c <= '9'.toInt()
}