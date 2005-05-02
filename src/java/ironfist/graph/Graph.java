package ironfist.graph;

import java.util.Iterator;

public interface Graph {

  public Iterator<Node> nodes();

  public Iterator<Edge> edges();

}