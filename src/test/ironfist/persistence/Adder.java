package ironfist.persistence;

class Adder implements Command {

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
  public Object execute(Reference object) {
    return new Long(((Counter) object.get()).add(value));
  }

}