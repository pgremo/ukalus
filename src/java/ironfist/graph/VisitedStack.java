package ironfist.graph;

import java.util.Stack;

public class VisitedStack<E> implements VisitedList<E> {

  private Stack<E> stack = new Stack<E>();

  public void add(E e) {
    stack.push(e);
  }

  public E get() {
    return stack.peek();
  }

  public void remove() {
    stack.pop();
  }

  public boolean isEmpty() {
    return stack.isEmpty();
  }
}
