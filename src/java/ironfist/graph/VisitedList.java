package ironfist.graph;

public interface VisitedList<E> {

  void add(E e);

  E get();

  void remove();

  boolean isEmpty();

}
