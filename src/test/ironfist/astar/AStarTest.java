/*
 * Created on Feb 15, 2005
 *
 */
package ironfist.astar;

import ironfist.math.Vector;
import junit.framework.TestCase;

/**
 * @author gremopm
 *  
 */
public class AStarTest extends TestCase {

  public void testFindPathNoObstacles() {
    Map map = new Map(new Object[][]{
        {Boolean.TRUE, Boolean.TRUE, Boolean.TRUE},
        {Boolean.TRUE, Boolean.TRUE, Boolean.TRUE},
        {Boolean.TRUE, Boolean.TRUE, Boolean.TRUE}});
    AStar finder = new AStar();
    Cost cost = new FixedCost(1);
    Node2D start = new Node2D(map, new Vector(0, 0), null);
    Node2D stop = new Node2D(map, new Vector(2, 2), null);
    Heuristic heuristic = new ManhattenHeuristic(start, stop);
    Node[] path = finder.solve(heuristic, cost, start, stop);
    assertEquals(5, path.length);

    assertEquals(new Vector(2, 2), ((Node2D) path[0]).getLocation());
    assertEquals(new Vector(2, 1), ((Node2D) path[1]).getLocation());
    assertEquals(new Vector(1, 1), ((Node2D) path[2]).getLocation());
    assertEquals(new Vector(0, 1), ((Node2D) path[3]).getLocation());
    assertEquals(new Vector(0, 0), ((Node2D) path[4]).getLocation());

  }

  public void testFindPathChoke() {
    AStar finder = new AStar();
    Cost cost = new FixedCost(1);
    Map map = new Map(new Object[][]{
        {Boolean.TRUE, null, Boolean.TRUE},
        {Boolean.TRUE, Boolean.TRUE, Boolean.TRUE},
        {Boolean.TRUE, null, Boolean.TRUE}});
    Node2D start = new Node2D(map, new Vector(0, 0), null);
    Node2D stop = new Node2D(map, new Vector(2, 2), null);
    Heuristic heuristic = new ManhattenHeuristic(start, stop);
    Node[] path = finder.solve(heuristic, cost, start, stop);
    assertEquals(5, path.length);

    assertEquals(new Vector(2, 2), ((Node2D) path[0]).getLocation());
    assertEquals(new Vector(1, 2), ((Node2D) path[1]).getLocation());
    assertEquals(new Vector(1, 1), ((Node2D) path[2]).getLocation());
    assertEquals(new Vector(1, 0), ((Node2D) path[3]).getLocation());
    assertEquals(new Vector(0, 0), ((Node2D) path[4]).getLocation());
  }
}