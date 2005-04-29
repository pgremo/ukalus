package ironfist.graph;

public class Edge {

  protected Node head;
  protected Node tail;

  protected Edge(Node head, Node tail) {
    this.head = head;
    this.tail = tail;
  }

  public Node getHead() {
    return head;
  }

  public Node getTail() {
    return tail;
  }
}