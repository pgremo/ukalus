package ukalus.graph;

import java.util.Arrays;
import java.util.List;

public class Edge {

  protected Node from;
  protected Node to;

  public Edge(Node from, Node to) {
    this.from = from;
    this.to = to;
  }

  public List<Node> getNodes() {
    return Arrays.asList(new Node[]{from, to});
  }

  public Node getNode(Node node) {
    Node result = null;
    if (node.equals(from)) {
      result = to;
    } else if (node.equals(to)) {
      result = from;
    }
    return result;
  }

}