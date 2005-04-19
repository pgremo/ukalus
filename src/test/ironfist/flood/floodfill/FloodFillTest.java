package ironfist.flood.floodfill;

import ironfist.loop.Level;
import ironfist.math.Vector2D;

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
    Set<Node> actual = fill.getTemplate(1,
      new Node(new Level(data), Vector2D.get(1, 1)));
    assertEquals(9, actual.size());
  }
  public void testFill1WithObstacles() {
    Object[][] data = new Object[][]{
        {FLOOR, WALL, FLOOR},
        {FLOOR, FLOOR, FLOOR},
        {FLOOR, WALL, FLOOR}};
    FloodFill fill = new FloodFill();
    Set<Node> actual = fill.getTemplate(1,
      new Node(new Level(data), Vector2D.get(1, 1)));
    assertEquals(7, actual.size());
  }
}
