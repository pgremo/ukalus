/*
 * Created on Mar 19, 2005
 *
 */
package ironfist.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.Iterator;

public class Tokens implements Iterable<String> {

  private Reader input;

  public Tokens(Reader input) {
    this.input = input;
  }

  public Iterator<String> iterator() {
    return new TokenIterator(input);
  }

  private class TokenIterator implements Iterator<String> {
    private StreamTokenizer tokenizer;
    private String next;

    public TokenIterator(Reader input) {
      tokenizer = new StreamTokenizer(input);
      tokenizer.wordChars('0', '9');
      getNext();
    }

    private void getNext() {
      next = null;
      try {
        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF && next == null) {
          if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
            next = tokenizer.sval;
          }
        }
      } catch (IOException e) {
      }
    }

    public boolean hasNext() {
      return next != null;
    }

    public String next() {
      String result = next;
      getNext();
      return result;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

  }

}
