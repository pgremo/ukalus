/*
 * Created on Feb 10, 2005
 *
 */
package ironfist.path.astar;

import ironfist.loop.Level;
import ironfist.math.Vector2D;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author gremopm
 * 
 */
public class Node2D implements Node {

  private static final Vector2D[] DIRECTIONS = new Vector2D[]{
      Vector2D.get(1, 0),
      Vector2D.get(0, 1),
      Vector2D.get(-1, 0),
      Vector2D.get(0, -1)};

  private Level level;
  private Vector2D location;
  private Node2D parent;
  private double g;
  private double h;

  public Node2D(Level map, Vector2D location, Node2D parent) {
    this.level = map;
    this.location = location;
    this.parent = parent;
  }

  public Vector2D getLocation() {
    return location;
  }

  public Node getParent() {
    return parent;
  }

  public Collection<Node> getSuccessors() {
    List<Node> result = new ArrayList<Node>(DIRECTIONS.length);
    for (Vector2D direction : DIRECTIONS) {
      Vector2D position = location.add(direction);
      if ((parent == null || !position.equals(parent.getLocation()))
          && level.contains(position) && level.get(position) != null) {
        result.add(new Node2D(level, position, this));
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
    return obj == null
        || (obj instanceof Node2D && ((Node2D) obj).getLocation()
          .equals(location));
  }

  public int hashCode() {
    return location.hashCode();
  }

  public String toString() {
    return location.toString();
  }
}