/*
 * Created on Feb 15, 2005
 *
 */
package ironfist.astar;

import junit.framework.TestCase;

/**
 * @author gremopm
 *  
 */
public class AStarTest extends TestCase {

  public void testFindPathNoObstacles() {
    int[][] map = new int[][]{
        {0, 0, 0},
        {0, 0, 0},
        {0, 0, 0}};
    AStar finder = new AStar();
    Cost cost = new FixedCost(1);
    Node2D start = new Node2D(map, 0, 0, null);
    Node2D stop = new Node2D(map, 2, 2, null);
    Heuristic heuristic = new ManhattenHeuristic(start, stop);
    Node[] path = finder.solve(heuristic, cost, start, stop);
    assertEquals(5, path.length);

    assertEquals(2, ((Node2D) path[0]).getX());
    assertEquals(2, ((Node2D) path[0]).getY());

    assertEquals(2, ((Node2D) path[1]).getX());
    assertEquals(1, ((Node2D) path[1]).getY());

    assertEquals(1, ((Node2D) path[2]).getX());
    assertEquals(1, ((Node2D) path[2]).getY());

    assertEquals(0, ((Node2D) path[3]).getX());
    assertEquals(1, ((Node2D) path[3]).getY());

    assertEquals(0, ((Node2D) path[4]).getX());
    assertEquals(0, ((Node2D) path[4]).getY());
  }

  public void testFindPathChoke() {
    AStar finder = new AStar();
    Cost cost = new FixedCost(1);
    int[][] map = new int[][]{{0, 1, 0}, {0, 0, 0}, {0, 1, 0}};
    Node2D start = new Node2D(map, 0, 0, null);
    Node2D stop = new Node2D(map, 2, 2, null);
    Heuristic heuristic = new ManhattenHeuristic(start, stop);
    Node[] path = finder.solve(heuristic, cost, start, stop);
    assertEquals(5, path.length);

    assertEquals(2, ((Node2D) path[0]).getX());
    assertEquals(2, ((Node2D) path[0]).getY());

    assertEquals(1, ((Node2D) path[1]).getX());
    assertEquals(2, ((Node2D) path[1]).getY());

    assertEquals(1, ((Node2D) path[2]).getX());
    assertEquals(1, ((Node2D) path[2]).getY());

    assertEquals(1, ((Node2D) path[3]).getX());
    assertEquals(0, ((Node2D) path[3]).getY());

    assertEquals(0, ((Node2D) path[4]).getX());
    assertEquals(0, ((Node2D) path[4]).getY());
  }
}