package ukalus.graph;

import java.util.List;

import static java.util.Arrays.asList;

public class Edge {

  private Node from;
  private Node to;

  public Edge(Node from, Node to) {
    this.from = from;
    this.to = to;
  }

  public List<Node> getNodes() {
    return asList(from, to);
  }

  public Node getNode(Node node) {
    if (node.equals(from)) {
      return to;
    } else if (node.equals(to)) {
      return from;
    }
    return null;
  }

}