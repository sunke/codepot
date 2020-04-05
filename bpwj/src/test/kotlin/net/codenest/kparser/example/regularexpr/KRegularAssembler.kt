package net.codenest.kparser.example.regularexpr

import com.sjm.parse.Parser
import com.sjm.parse.Sequence
import net.codenest.kparser.parsing.KAssembly
import net.codenest.kparser.parsing.KCharAssembler
import net.codenest.kparser.parsing.KParser
import net.codenest.kparser.parsing.KSequence

class KAndAssembler: KCharAssembler() {

    /**
     * Pop two parsers from the stack and push a new `Sequence` of them.
     */
    override fun workOn(aseembly: KAssembly<Char>) {
        val top = aseembly.pop()
        val seq = KSequence<Char>()
        //seq.add(aseembly.pop() as KParser)
        //seq.add(top as Parser)
        aseembly.push(seq)
    }
}
