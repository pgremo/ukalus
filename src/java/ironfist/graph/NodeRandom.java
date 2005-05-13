package ironfist.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NodeRandom implements NodeCollection {

  private Random random;
  private List<Node> storage = new ArrayList<Node>();

  public NodeRandom(Random random) {
    this.random = random;
  }

  public void add(Node e) {
    if (storage.isEmpty()) {
      storage.add(e);
    } else {
      storage.add(random.nextInt(storage.size()), e);
    }
  }

  public Node get() {
    Node result = null;
    if (!storage.isEmpty()) {
      result = storage.get(0);
    }
    return result;
  }

  public void remove() {
    if (!storage.isEmpty()) {
      storage.remove(0);
    }
  }

  public boolean isEmpty() {
    return storage.isEmpty();
  }

  public String toString() {
    return storage.toString();
  }
}
