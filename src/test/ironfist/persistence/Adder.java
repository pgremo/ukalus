package ironfist.persistence;

import ironfist.util.Closure;

class Adder implements Closure<Reference, Object> {

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
    return new Long(((Counter) object.get()).add(value));
  }

}