/*
 * Created on Feb 11, 2005
 *
 */
package ironfist.astar;

import junit.framework.TestCase;

/**
 * @author gremopm
 *  
 */
public class Node2DTest extends TestCase {

  public void testGetSuccessorsFromMiddle() {
    int[][] map = new int[3][3];
    Node2D parent = new Node2D(map, 1, 0, null);
    Node2D current = new Node2D(map, 1, 1, parent);
    Node[] successors = current.getSuccessors();
    assertEquals(3, successors.length);
  }

  public void testGetSuccessorsFromCorner() {
    int[][] map = new int[3][3];
    Node2D parent = new Node2D(map, 1, 0, null);
    Node2D current = new Node2D(map, 0, 0, parent);
    Node[] successors = current.getSuccessors();
    assertEquals(1, successors.length);
  }

  public void testGetSuccessorsFromEdge() {
    int[][] map = new int[3][3];
    Node2D parent = new Node2D(map, 0, 0, null);
    Node2D current = new Node2D(map, 0, 1, parent);
    Node[] successors = current.getSuccessors();
    assertEquals(2, successors.length);
  }

  public void testGetSuccessorsOriginInMiddle() {
    int[][] map = new int[3][3];
    Node2D current = new Node2D(map, 1, 1, null);
    Node[] successors = current.getSuccessors();
    assertEquals(4, successors.length);
  }
}