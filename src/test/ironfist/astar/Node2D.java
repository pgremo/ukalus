/*
 * Created on Feb 10, 2005
 *
 */
package ironfist.astar;

import ironfist.math.Vector;

import java.util.ArrayList;
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

  public Node[] getSuccessors() {
    List result = new ArrayList(DIRECTIONS.length);
    for (int i = 0; i < DIRECTIONS.length; i++) {
      Vector position = location.add(DIRECTIONS[i]);
      if ((parent == null || !position.equals(parent.getLocation())) // not
          // parent
          && map.contains(position) && map.get(position) != null // passable
      ) {
        result.add(new Node2D(map, position, this));
      }
    }
    return (Node[]) result.toArray(new Node[result.size()]);
  }

  public void setG(double g) {
    this.g = g;
  }

  public double getG() {
    return g;
  }

  public double getF() {
    return g + h;
  }

  public double getH() {
    return h;
  }

  public void setH(double h) {
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