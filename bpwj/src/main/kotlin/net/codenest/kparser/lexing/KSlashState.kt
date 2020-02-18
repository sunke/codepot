package net.codenest.kparser.lexing


class KSlashState : KTokenizerState {
    override fun nextToken(ch: Char, tokenizer: KTokenizer): KToken {
        require(tokenizer.getState(ch.toInt()) is KSlashState)

        val reader = tokenizer.reader
        var cin = reader.read()

        // '/*' state ignores everything up to a closing '*/', and then returns the tokenizer's next token.
        if (cin == '*'.toInt()) {
            var c = reader.read()
            var prev = 0
            while (c >= 0) {
                if (prev == '*'.toInt() && c == '/'.toInt()) {
                    break;
                }
                prev = c
                c = reader.read()
            }
        }

        //  '//' state ignores everything up to an end-of-line and returns the tokenizer's next token.
        if (cin == '/'.toInt()) {
            var c = reader.read()
            while (c != '\n'.toInt() && c != '\r'.toInt() && c >= 0) {
                c = reader.read()
            }
        }


        return tokenizer.nextToken()
    }
}