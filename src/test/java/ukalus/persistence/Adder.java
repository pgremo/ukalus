package ukalus.persistence;

import java.io.Serializable;
import java.util.function.Function;

class Adder implements Function<Reference, Object>, Serializable {

  private static final long serialVersionUID = 3256999939061199155L;
  private long value;

  Adder(long value) {
    this.value = value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see persistence.Command#execute(java.lang.Object)
   */
  public Object apply(Reference object) {
    return ((Counter) object.get()).add(value);
  }

}