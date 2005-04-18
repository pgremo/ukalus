package ironfist.blast.raytrace;

import ironfist.loop.Level;
import ironfist.math.Vector2D;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

public class RayTracingTest extends TestCase {

  private static final Object WALL = null;
  private static final Object FLOOR = new Object();

  public void testSeeAll() {
    Object[][] area = new Object[][]{
        {FLOOR, FLOOR, FLOOR, FLOOR, FLOOR},
        {FLOOR, FLOOR, FLOOR, FLOOR, FLOOR},
        {FLOOR, FLOOR, FLOOR, FLOOR, FLOOR},
        {FLOOR, FLOOR, FLOOR, FLOOR, FLOOR},
        {FLOOR, FLOOR, FLOOR, FLOOR, FLOOR}};
    Set<Vector2D> expected = new HashSet<Vector2D>();
    for (int i = 0; i < area.length; i++) {
      for (int j = 0; j < area[i].length; j++) {
        expected.add(Vector2D.get(i, j));
      }
    }
    expected.remove(Vector2D.get(0, 0));
    expected.remove(Vector2D.get(4, 0));
    expected.remove(Vector2D.get(0, 4));
    expected.remove(Vector2D.get(4, 4));

    Level level = new Level(area);
    Set<Vector2D> actual = new RayTracing().getArea(2, new LevelScanner(level),
      Vector2D.get(2, 2));
    printSight(actual, level);
    assertNotNull(actual);
    assertTrue(actual.containsAll(expected));
    assertEquals(actual.size(), expected.size());
  }

  public void testSeeAllWalls() {
    Object[][] area = new Object[][]{
        {WALL, WALL, WALL, WALL, WALL},
        {WALL, FLOOR, FLOOR, FLOOR, WALL},
        {WALL, FLOOR, FLOOR, FLOOR, WALL},
        {WALL, FLOOR, FLOOR, FLOOR, WALL},
        {WALL, WALL, WALL, WALL, WALL}};
    Set<Vector2D> expected = new HashSet<Vector2D>();
    for (int i = 0; i < area.length; i++) {
      for (int j = 0; j < area[i].length; j++) {
        expected.add(Vector2D.get(i, j));
      }
    }
    expected.remove(Vector2D.get(0, 0));
    expected.remove(Vector2D.get(4, 0));
    expected.remove(Vector2D.get(0, 4));
    expected.remove(Vector2D.get(4, 4));

    Level level = new Level(area);
    Set<Vector2D> actual = new RayTracing().getArea(2, new LevelScanner(level),
      Vector2D.get(2, 2));
    printSight(actual, level);
    assertNotNull(actual);
    assertTrue(actual.containsAll(expected));
    assertEquals(actual.size(), expected.size());
  }

  public void testSeeAllCorners() {
    Object[][] area = new Object[][]{
        {WALL, WALL, WALL, WALL, WALL},
        {WALL, FLOOR, FLOOR, FLOOR, WALL},
        {WALL, FLOOR, FLOOR, FLOOR, WALL},
        {WALL, FLOOR, FLOOR, FLOOR, WALL},
        {WALL, WALL, WALL, WALL, WALL}};
    Set<Vector2D> expected = new HashSet<Vector2D>();
    for (int i = 0; i < area.length; i++) {
      for (int j = 0; j < area[i].length; j++) {
        expected.add(Vector2D.get(i, j));
      }
    }

    Level level = new Level(area);
    Set<Vector2D> actual = new RayTracing().getArea(3, new LevelScanner(level),
      Vector2D.get(2, 2));
    printSight(actual, level);
    assertNotNull(actual);
    assertTrue(actual.containsAll(expected));
    assertEquals(actual.size(), expected.size());
  }

  public void testSeeAllWallsSmallRoom() {
    Object[][] area = new Object[][]{
        {WALL, WALL, WALL, WALL, WALL, WALL, WALL},
        {WALL, WALL, WALL, WALL, WALL, WALL, WALL},
        {WALL, WALL, FLOOR, FLOOR, FLOOR, WALL, WALL},
        {WALL, WALL, FLOOR, FLOOR, FLOOR, WALL, WALL},
        {WALL, WALL, FLOOR, FLOOR, FLOOR, WALL, WALL},
        {WALL, WALL, WALL, WALL, WALL, WALL, WALL},
        {WALL, WALL, WALL, WALL, WALL, WALL, WALL}};
    Set<Vector2D> expected = new HashSet<Vector2D>();
    for (int i = 1; i < area.length - 1; i++) {
      for (int j = 1; j < area[i].length - 1; j++) {
        expected.add(Vector2D.get(i, j));
      }
    }
    expected.remove(Vector2D.get(1, 1));
    expected.remove(Vector2D.get(5, 1));
    expected.remove(Vector2D.get(1, 5));
    expected.remove(Vector2D.get(5, 5));

    Level level = new Level(area);
    Set<Vector2D> actual = new RayTracing().getArea(2, new LevelScanner(level),
      Vector2D.get(3, 3));
    printSight(actual, level);
    assertNotNull(actual);
    assertTrue(actual.containsAll(expected));
    assertEquals(actual.size(), expected.size());
  }

  public void testSeePartialRoom() {
    Object[][] area = new Object[][]{
        {WALL, WALL, WALL, WALL, WALL, WALL},
        {WALL, WALL, WALL, FLOOR, FLOOR, WALL},
        {WALL, WALL, WALL, FLOOR, FLOOR, WALL},
        {WALL, FLOOR, FLOOR, FLOOR, FLOOR, WALL},
        {WALL, WALL, WALL, FLOOR, FLOOR, WALL},
        {WALL, WALL, WALL, FLOOR, FLOOR, WALL},
        {WALL, WALL, WALL, WALL, WALL, WALL}};
    Set<Vector2D> expected = new HashSet<Vector2D>();
    for (int i = 0; i < area.length; i++) {
      for (int j = 0; j < area[i].length; j++) {
        expected.add(Vector2D.get(i, j));
      }
    }
    Level level = new Level(area);
    Set<Vector2D> actual = new RayTracing().getArea(5, new LevelScanner(level),
      Vector2D.get(3, 1));
    printSight(actual, level);
    assertNotNull(actual);
    assertTrue(actual.containsAll(expected));
    assertEquals(actual.size(), expected.size());
  }

  private void printSight(Set<Vector2D> list, Level level) {
    for (int i = 0; i < level.getLength(); i++) {
      for (int j = 0; j < level.getWidth(); j++) {
        Vector2D location = Vector2D.get(i, j);
        if (list.contains(location)) {
          System.out.print(level.get(location) == null ? "#" : ".");
        } else {
          System.out.print(" ");
        }
      }
      System.out.println();
    }
  }

}
