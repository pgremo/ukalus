/*
 * Created on Feb 10, 2005
 *
 */
package ironfist.astar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gremopm
 *  
 */
public class Node2D implements Node {

  private int[][] map;
  private int x;
  private int y;
  private Node2D parent;
  private double g;
  private double h;

  public Node2D(int[][] map, int x, int y, Node2D parent) {
    this.map = map;
    this.x = x;
    this.y = y;
    this.parent = parent;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Node getParent() {
    return parent;
  }

  public Node[] getSuccessors() {
    List result = new ArrayList(3);
    if (x > 0 && (parent == null || x - 1 != parent.getX()) && map[x - 1][y] != 1) {
      result.add(new Node2D(map, x - 1, y, this));
    }
    if (x < map.length - 1 && (parent == null || x + 1 != parent.getX()) && map[x + 1][y] != 1) {
      result.add(new Node2D(map, x + 1, y, this));
    }
    if (y > 0 && (parent == null || y - 1 != parent.getY()) && map[x][y - 1] != 1) {
      result.add(new Node2D(map, x, y - 1, this));
    }
    if (y < map[x].length - 1 && (parent == null || y + 1 != parent.getY()) && map[x][y + 1] != 1) {
      result.add(new Node2D(map, x, y + 1, this));
    }
    return (Node[]) result.toArray(new Node[result.size()]);
  }

  public int compareTo(Object o) {
    return 0;
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
    return obj != null && obj instanceof Node2D && ((Node2D) obj).getX() == x
        && ((Node2D) obj).getY() == y;
  }

  public int hashCode() {
    return x + y;
  }
  
  public String toString() {
    return "(x=" + x + ",y=" + y + ")";
  }
}