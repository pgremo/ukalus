package ironfist.util;


public class IsEqual<E> implements Closure<E, Boolean> {

  private static final long serialVersionUID = 3256439213917484343L;
  private Object target;

  public IsEqual(Object target) {
    this.target = target;
  }

  public Boolean apply(E value) {
    return target == value || (target != null && target.equals(value));
  }

}
