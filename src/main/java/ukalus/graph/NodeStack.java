package ukalus.graph;

import java.util.LinkedList;

public class NodeStack implements NodeCollection {

  private LinkedList<Node> storage = new LinkedList<Node>();

  public void add(Node e) {
    storage.addFirst(e);
  }

  public Node get() {
    return storage.peek();
  }

  public void remove() {
    storage.poll();
  }

  public boolean isEmpty() {
    return storage.isEmpty();
  }

  public String toString() {
    return storage.toString();
  }
}