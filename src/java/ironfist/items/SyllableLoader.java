/*
 * Created on Mar 19, 2005
 *
 */
package ironfist.items;

import ironfist.util.Closure;
import ironfist.util.MarkovChain;
import ironfist.util.Syllables;

public class SyllableLoader implements Closure<String, Object> {

  private static final long serialVersionUID = 3906648609259598642L;
  private MarkovChain<String> chains;

  public SyllableLoader(MarkovChain<String> chains) {
    this.chains = chains;
  }

  public Object apply(String item) {
    chains.addAll(new Syllables(item.toLowerCase()));
    return null;
  }

}
