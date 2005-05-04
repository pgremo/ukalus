package ironfist.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VisitedRandom<E> implements VisitedList<E> {

  private Random random;
  private List<E> list = new ArrayList<E>();

  public VisitedRandom(Random random) {
    this.random = random;
  }

  public void add(E e) {
    if (list.isEmpty()) {
      list.add(e);
    } else {
      list.add(random.nextInt(list.size()), e);
    }
  }

  public E get() {
    E result = null;
    if (!list.isEmpty()) {
      result = list.get(0);
    }
    return result;
  }

  public void remove() {
    if (!list.isEmpty()) {
      list.remove(0);
    }
  }

  public boolean isEmpty() {
    return list.isEmpty();
  }
}
