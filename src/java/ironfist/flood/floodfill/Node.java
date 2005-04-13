package ironfist.flood.floodfill;

import ironfist.loop.Level;
import ironfist.math.Vector;

import java.util.ArrayList;
import java.util.List;

public class Node {

  private static final Vector[] DIRECTIONS = new Vector[]{
      new Vector(1, 1),
      new Vector(1, 0),
      new Vector(1, -1),
      new Vector(0, 1),
      new Vector(0, -1),
      new Vector(-1, 1),
      new Vector(-1, 0),
      new Vector(-1, -1)};

  private Level level;
  private Vector location;
  private int distance;

  public Node(Level level, Vector vector) {
    this.level = level;
    this.location = vector;
  }

  public Node[] getChildren() {
    List<Node> result = new ArrayList<Node>(4);
    for (Vector current : DIRECTIONS) {
      Vector target = location.add(current);
      if (level.contains(target) && level.get(target) != null) {
        result.add(new Node(level, target));
      }
    }
    return result.toArray(new Node[result.size()]);
  }

  public Vector getLocation() {
    return location;
  }

  public int getDistance() {
    return distance;
  }

  public void setDistance(int distance) {
    this.distance = distance;
  }

  public boolean equals(Object obj) {
    return this == obj || (obj instanceof Node && ((Node) obj).getLocation()
      .equals(location));
  }

  public int hashCode() {
    return location.hashCode();
  }

  public String toString() {
    return location.toString() + "=" + distance;
  }

}
