/*
 * Created on Feb 15, 2005
 *
 */
package ironfist.astar;

import ironfist.loop.Level;
import ironfist.math.Vector;

import java.util.Iterator;

import junit.framework.TestCase;

/**
 * @author gremopm
 *  
 */
public class AStarTest extends TestCase {

  public void testFindPathNoObstacles() {
    Level map = new Level(new Object[][]{
        {Boolean.TRUE, Boolean.TRUE, Boolean.TRUE},
        {Boolean.TRUE, Boolean.TRUE, Boolean.TRUE},
        {Boolean.TRUE, Boolean.TRUE, Boolean.TRUE}});
    AStar finder = new AStar();
    Cost cost = new FixedCost(1);
    Node2D start = new Node2D(map, new Vector(0, 0), null);
    Node2D stop = new Node2D(map, new Vector(2, 2), null);
    Heuristic heuristic = new ManhattenHeuristic(start, stop);
    Iterator path = finder.solve(heuristic, cost, start, stop);
    assertTrue(path.hasNext());

    assertEquals(new Vector(2, 2), ((Node2D) path.next()).getLocation());
    assertEquals(new Vector(1, 2), ((Node2D) path.next()).getLocation());
    assertEquals(new Vector(1, 1), ((Node2D) path.next()).getLocation());
    assertEquals(new Vector(1, 0), ((Node2D) path.next()).getLocation());
    assertEquals(new Vector(0, 0), ((Node2D) path.next()).getLocation());

  }

  public void testFindPathChoke() {
    AStar finder = new AStar();
    Cost cost = new FixedCost(1);
    Level map = new Level(new Object[][]{
        {Boolean.TRUE, null, Boolean.TRUE},
        {Boolean.TRUE, Boolean.TRUE, Boolean.TRUE},
        {Boolean.TRUE, null, Boolean.TRUE}});
    Node2D start = new Node2D(map, new Vector(0, 0), null);
    Node2D stop = new Node2D(map, new Vector(2, 2), null);
    Heuristic heuristic = new ManhattenHeuristic(start, stop);
    Iterator path = finder.solve(heuristic, cost, start, stop);
    assertTrue(path.hasNext());

    assertEquals(new Vector(2, 2), ((Node2D) path.next()).getLocation());
    assertEquals(new Vector(1, 2), ((Node2D) path.next()).getLocation());
    assertEquals(new Vector(1, 1), ((Node2D) path.next()).getLocation());
    assertEquals(new Vector(1, 0), ((Node2D) path.next()).getLocation());
    assertEquals(new Vector(0, 0), ((Node2D) path.next()).getLocation());
  }
}