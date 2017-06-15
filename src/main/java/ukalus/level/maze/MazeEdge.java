package ukalus.level.maze;

import ukalus.graph.Edge;
import ukalus.graph.Node;
import ukalus.math.Vector2D;

public class MazeEdge extends Edge {

  private Vector2D location;

  public MazeEdge(Vector2D location, Node head, Node tail) {
    super(head, tail);
    this.location = location;
  }

  public Vector2D getLocation() {
    return location;
  }

  public String toString() {
    return location.toString();
  }
}
