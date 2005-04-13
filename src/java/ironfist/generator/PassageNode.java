/*
 * Created on Feb 10, 2005
 *
 */
package ironfist.generator;

import ironfist.loop.Level;
import ironfist.math.Vector;
import ironfist.path.astar.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author gremopm
 * 
 */
public class PassageNode implements Node {

  private static final List<Vector> DIRECTIONS = new ArrayList<Vector>(4);

  static {
    DIRECTIONS.add(new Vector(1, 0));
    DIRECTIONS.add(new Vector(0, 1));
    DIRECTIONS.add(new Vector(-1, 0));
    DIRECTIONS.add(new Vector(0, -1));
  }

  private Level map;
  private Vector location;
  private PassageNode parent;
  private double g;
  private double h;

  public PassageNode(Level map, Vector location, PassageNode parent) {
    this.map = map;
    this.location = location;
    this.parent = parent;
  }

  public Vector getLocation() {
    return location;
  }

  public Node getParent() {
    return parent;
  }

  public Collection<Node> getSuccessors() {
    List<Node> result = new ArrayList<Node>(DIRECTIONS.size());
    for (Vector position : DIRECTIONS) {
      Vector right = location.add(position.orthoganal());
      Vector left = location.add(position.orthoganal()
        .multiply(-1));
      if ((parent == null || !position.equals(parent.getLocation())) // not
          // parent
          && map.contains(position) // exists
          && (!map.contains(right) || map.get(right) == null || map.get(right) == Feature.ROOM)
          && (!map.contains(left) || map.get(left) == null || map.get(left) == Feature.ROOM)) {
        result.add(new PassageNode(map, position, this));
      }
    }
    return result;
  }

  public void setCost(double g) {
    this.g = g;
  }

  public double getCost() {
    return g;
  }

  public double getTotal() {
    return g + h;
  }

  public double getEstimate() {
    return h;
  }

  public void setEstimate(double h) {
    this.h = h;
  }

  public boolean equals(Object obj) {
    return obj != null && obj instanceof PassageNode
        && ((PassageNode) obj).getLocation()
          .equals(location);
  }

  public int hashCode() {
    return location.hashCode();
  }

  public String toString() {
    return location.toString();
  }
}