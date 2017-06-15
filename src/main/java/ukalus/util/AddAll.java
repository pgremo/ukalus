/*
 * Created on Mar 20, 2005
 *
 */
package ukalus.util;


import java.util.Collection;

public class AddAll<E> implements Closure<E, Object> {

  private static final long serialVersionUID = 4050487828506818613L;

  private Collection<E> storage;

  public AddAll(Collection<E> storage) {
    this.storage = storage;
  }

  public Object apply(E value) {
    storage.add(value);
    return null;
  }

}
