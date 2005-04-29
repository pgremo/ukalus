package ironfist.graph;

public abstract class GraphTraversal {

  protected Graph graph;
  protected GraphTraversalDelegate delegate;

  protected GraphTraversal(Graph graph, GraphTraversalDelegate delegate) {
    this.graph = graph;
    this.delegate = delegate;
  }

  public abstract void start(Node root);

}