package ironfist.level.maze.sparcecursal;

import ironfist.graph.VisitedList;

public class VisitedLast<E> implements VisitedList<E> {

  private E last;

  public void add(E e) {
    last = e;
  }

  public E get() {
    return last;
  }

  public void remove() {
    last = null;
  }

  public boolean isEmpty() {
    return last == null;
  }

}
