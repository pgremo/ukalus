/*
 * Created on Mar 18, 2005
 *
 */
package ukalus.persistence;

import java.util.function.Consumer;
import java.util.function.Function;

public class ReplayLog implements Consumer<Function> {
  private Reference reference;

  public ReplayLog(Reference reference) {
    this.reference = reference;
  }

  @Override
  public void accept(Function function) {
    try {
      function.apply(reference);
    } catch (Exception e) {
    }
  }
}
