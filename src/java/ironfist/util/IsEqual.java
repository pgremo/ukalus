package ironfist.util;

public class IsEqual implements Constraint {

  private Object target;

  public IsEqual(Object target) {
    this.target = target;
  }

  public boolean allow(Object value) {
    return target == value || (target != null && target.equals(value));
  }

}
