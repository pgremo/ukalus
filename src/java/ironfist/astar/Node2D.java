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
  private int g;
  private int h;

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
    if (x > 0 && (parent == null || x - 1 != parent.getX())) {
      result.add(new Node2D(map, x - 1, y, this));
    }
    if (x < map.length - 1 && (parent == null || x + 1 != parent.getX())) {
      result.add(new Node2D(map, x + 1, y, this));
    }
    if (y > 0 && (parent == null || y - 1 != parent.getY())) {
      result.add(new Node2D(map, x, y - 1, this));
    }
    if (y < map[x].length - 1 && (parent == null || y + 1 != parent.getY())) {
      result.add(new Node2D(map, x, y + 1, this));
    }
    return (Node[]) result.toArray(new Node[result.size()]);
  }

  public int compareTo(Object o) {
    return 0;
  }

  public void setG(int g) {
    this.g = g;
  }

  public int getG() {
    return g;
  }

  public int getF() {
    return g + h;
  }

  public int getH() {
    return h;
  }

  public void setH(int h) {
    this.h = h;
  }

  public boolean equals(Object obj) {
    return obj instanceof Node2D && ((Node2D) obj).getX() == x
        && ((Node2D) obj).getY() == y;
  }

  public int hashCode() {
    return x + y;
  }
}