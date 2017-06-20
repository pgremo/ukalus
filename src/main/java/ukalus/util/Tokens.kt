/*
 * Created on Mar 19, 2005
 *
 */
package ukalus.util

import java.io.IOException
import java.io.Reader
import java.io.StreamTokenizer
import java.io.StreamTokenizer.TT_EOF
import java.io.StreamTokenizer.TT_WORD

class Tokens(private val input: Reader) : Iterable<String> {

    override fun iterator(): Iterator<String> {
        return object : AbstractIterator<String>() {
            private val tokenizer: StreamTokenizer = StreamTokenizer(input).apply {
                wordChars('0'.toInt(), '9'.toInt())
            }

            override fun computeNext() {
                try {
                    while (tokenizer.nextToken() != TT_EOF) {
                        if (tokenizer.ttype == TT_WORD) {
                            setNext(tokenizer.sval)
                            break
                        }
                    }
                    if (tokenizer.ttype == TT_EOF) done()
                } catch (e: IOException) {
                }
            }
        }
    }
}
