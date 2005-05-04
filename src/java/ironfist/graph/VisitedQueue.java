package ironfist.graph;

import java.util.LinkedList;
import java.util.Queue;

public class VisitedQueue<E> implements VisitedList<E> {

  private Queue<E> queue = new LinkedList<E>();

  public void add(E e) {
    queue.add(e);
  }

  public E get() {
    return queue.peek();
  }

  public void remove() {
    queue.poll();
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }
}
