package ironfist.container;

public interface Closure<E> {

  void invoke(E item);
}
