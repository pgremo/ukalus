/*
 * Created on Mar 18, 2005
 *
 */
package ukalus.persistence;

import ukalus.util.Closure;

public class ReplayLog implements
    Closure<Closure<Reference, Object>, Object> {
  private static final long serialVersionUID = 3256719580893165877L;
  private Reference reference;

  public ReplayLog(Reference reference) {
    this.reference = reference;
  }

  public Object apply(Closure<Reference, Object> item) {
    try {
      item.apply(reference);
    } catch (Exception e) {
    }
    return null;
  }

}
