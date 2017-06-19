package ukalus.flood.floodfill;

import ukalus.level.Level;
import ukalus.math.Vector2D;

import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;

public class FloodFillTest extends TestCase {

  private static final Object FLOOR = new Object();
  private static final Object WALL = null;

  public void testFill1() {
    Object[][] data = new Object[][]{
        {FLOOR, FLOOR, FLOOR},
        {FLOOR, FLOOR, FLOOR},
        {FLOOR, FLOOR, FLOOR}};
    FloodFill fill = new FloodFill();
    Set<Node> actual = fill.getTemplate(1, new Node(new Level<>(data),
      Vector2D.Companion.get(1, 1), 0));
    assertEquals(9, actual.size());
  }

  public void testFill1WithObstacles() {
    Object[][] data = new Object[][]{
        {FLOOR, WALL, FLOOR},
        {FLOOR, FLOOR, FLOOR},
        {FLOOR, WALL, FLOOR}};
    FloodFill fill = new FloodFill();
    Set<Node> actual = fill.getTemplate(1, new Node(new Level<>(data),
      Vector2D.Companion.get(1, 1), 0));
    assertEquals(7, actual.size());
  }

  public void testFill1WithBlock() {
    Object[][] data = new Object[][]{
        {FLOOR, WALL, FLOOR, FLOOR},
        {FLOOR, FLOOR, FLOOR, FLOOR},
        {FLOOR, FLOOR, WALL, FLOOR},
        {FLOOR, FLOOR, FLOOR, FLOOR},
        {FLOOR, WALL, FLOOR, FLOOR}};
    FloodFill fill = new FloodFill();
    Set<Node> actual = fill.getTemplate(10, new Node(new Level<>(data),
      Vector2D.Companion.get(2, 0), 0));
    assertEquals(17, actual.size());

    Iterator<Node> iterator = actual.iterator();
    Node target = null;
    while (iterator.hasNext() && target == null) {
      Node current = iterator.next();
      if (current.getLocation()
        .equals(Vector2D.Companion.get(2, 3))) {
        target = current;
      }
    }

    assertNotNull(target);
    assertEquals(3, target.getDistance());
  }
}
