/*
 * Created on Feb 10, 2005
 *
 */
package ironfist.path.astar;

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
public class Node2D implements Node {

  private static final Vector[] DIRECTIONS = new Vector[]{
      new Vector(1, 0),
      new Vector(0, 1),
      new Vector(-1, 0),
      new Vector(0, -1)};

  private Level map;
  private Vector location;
  private Node2D parent;
  private double g;
  private double h;

  public Node2D(Level map, Vector location, Node2D parent) {
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
    List<Node> result = new ArrayList<Node>(DIRECTIONS.length);
    for (Vector direction : DIRECTIONS) {
      Vector position = location.add(direction);
      if ((parent == null || !position.equals(parent.getLocation())) // not
          // parent
          && map.contains(position) && map.get(position) != null // passable
      ) {
        result.add(new Node2D(map, position, this));
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
    return obj != null && obj instanceof Node2D && ((Node2D) obj).getLocation()
      .equals(location);
  }

  public int hashCode() {
    return location.hashCode();
  }

  public String toString() {
    return location.toString();
  }
}