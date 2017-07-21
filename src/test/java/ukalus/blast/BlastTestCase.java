package ukalus.blast;

import org.junit.Test;
import ukalus.level.Level;
import ukalus.math.Vector2D;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

abstract public class BlastTestCase {

  private static final Object WALL = null;
  private static final Object FLOOR = new Object();

  protected Blast blast;

  @Test
  public void testSeeAll() {
    Object[][] area = new Object[][]{
      {FLOOR, FLOOR, FLOOR, FLOOR, FLOOR},
      {FLOOR, FLOOR, FLOOR, FLOOR, FLOOR},
      {FLOOR, FLOOR, FLOOR, FLOOR, FLOOR},
      {FLOOR, FLOOR, FLOOR, FLOOR, FLOOR},
      {FLOOR, FLOOR, FLOOR, FLOOR, FLOOR}};
    Set<Vector2D> expected = new HashSet<>();
    for (int i = 0; i < area.length; i++) {
      for (int j = 0; j < area[i].length; j++) {
        expected.add(new Vector2D(i, j));
      }
    }
    expected.remove(new Vector2D(0, 0));
    expected.remove(new Vector2D(4, 0));
    expected.remove(new Vector2D(0, 4));
    expected.remove(new Vector2D(4, 4));

    Level<Object> level = new Level<>(area);
    Set<Vector2D> actual = blast.getTemplate(new Vector2D(2, 2),
      new LevelScanner(level), 2);
    printSight(actual, level);
    assertNotNull(actual);
    assertTrue(actual.containsAll(expected));
    assertEquals(actual.size(), expected.size());
  }

  @Test
  public void testSeeAllWalls() {
    Object[][] area = new Object[][]{
      {WALL, WALL, WALL, WALL, WALL},
      {WALL, FLOOR, FLOOR, FLOOR, WALL},
      {WALL, FLOOR, FLOOR, FLOOR, WALL},
      {WALL, FLOOR, FLOOR, FLOOR, WALL},
      {WALL, WALL, WALL, WALL, WALL}};
    Set<Vector2D> expected = new HashSet<>();
    for (int i = 0; i < area.length; i++) {
      for (int j = 0; j < area[i].length; j++) {
        expected.add(new Vector2D(i, j));
      }
    }
    expected.remove(new Vector2D(0, 0));
    expected.remove(new Vector2D(4, 0));
    expected.remove(new Vector2D(0, 4));
    expected.remove(new Vector2D(4, 4));

    Level<Object> level = new Level<>(area);
    Set<Vector2D> actual = blast.getTemplate(new Vector2D(2, 2),
      new LevelScanner(level), 2);
    printSight(actual, level);
    assertNotNull(actual);
    assertTrue(actual.containsAll(expected));
    assertEquals(actual.size(), expected.size());
  }

  @Test
  public void testSeeAllCorners() {
    Object[][] area = new Object[][]{
      {WALL, WALL, WALL, WALL, WALL},
      {WALL, FLOOR, FLOOR, FLOOR, WALL},
      {WALL, FLOOR, FLOOR, FLOOR, WALL},
      {WALL, FLOOR, FLOOR, FLOOR, WALL},
      {WALL, WALL, WALL, WALL, WALL}};
    Set<Vector2D> expected = new HashSet<>();
    for (int i = 0; i < area.length; i++) {
      for (int j = 0; j < area[i].length; j++) {
        expected.add(new Vector2D(i, j));
      }
    }

    Level<Object> level = new Level<>(area);
    Set<Vector2D> actual = blast.getTemplate(new Vector2D(2, 2), new LevelScanner(level), 3);
    printSight(actual, level);
    assertNotNull(actual);
    assertTrue(actual.containsAll(expected));
    assertEquals(expected.size(), actual.size());
  }

  @Test
  public void testSeeAllCornersSmall() {
    Object[][] area = new Object[][]{
      {WALL, WALL, WALL},
      {WALL, FLOOR, WALL},
      {WALL, WALL, WALL}};
    Set<Vector2D> expected = new HashSet<>();
    for (int i = 0; i < area.length; i++) {
      for (int j = 0; j < area[i].length; j++) {
        expected.add(new Vector2D(i, j));
      }
    }

    Level<Object> level = new Level<>(area);
    Set<Vector2D> actual = blast.getTemplate(new Vector2D(1, 1), new LevelScanner(level), 3);
    printSight(actual, level);
    assertNotNull(actual);
    assertTrue(actual.containsAll(expected));
    assertEquals(actual.size(), expected.size());
  }

  @Test
  public void testSeeAllWallsSmallRoom() {
    Object[][] area = new Object[][]{
      {WALL, WALL, WALL, WALL, WALL, WALL, WALL},
      {WALL, WALL, WALL, WALL, WALL, WALL, WALL},
      {WALL, WALL, FLOOR, FLOOR, FLOOR, WALL, WALL},
      {WALL, WALL, FLOOR, FLOOR, FLOOR, WALL, WALL},
      {WALL, WALL, FLOOR, FLOOR, FLOOR, WALL, WALL},
      {WALL, WALL, WALL, WALL, WALL, WALL, WALL},
      {WALL, WALL, WALL, WALL, WALL, WALL, WALL}};
    Set<Vector2D> expected = new HashSet<>();
    for (int i = 1; i < area.length - 1; i++) {
      for (int j = 1; j < area[i].length - 1; j++) {
        expected.add(new Vector2D(i, j));
      }
    }
    expected.remove(new Vector2D(1, 1));
    expected.remove(new Vector2D(5, 1));
    expected.remove(new Vector2D(1, 5));
    expected.remove(new Vector2D(5, 5));

    Level<Object> level = new Level<>(area);
    Set<Vector2D> actual = blast.getTemplate(new Vector2D(3, 3), new LevelScanner(level), 2);
    printSight(actual, level);
    assertNotNull(actual);
    assertTrue(actual.containsAll(expected));
    assertEquals(actual.size(), expected.size());
  }

  @Test
  public void testSeePartialRoom() {
    Object[][] area = new Object[][]{
      {WALL, WALL, WALL, WALL, WALL, WALL},
      {WALL, WALL, WALL, FLOOR, FLOOR, WALL},
      {WALL, WALL, WALL, FLOOR, FLOOR, WALL},
      {WALL, FLOOR, FLOOR, FLOOR, FLOOR, WALL},
      {WALL, WALL, WALL, FLOOR, FLOOR, WALL},
      {WALL, WALL, WALL, FLOOR, FLOOR, WALL},
      {WALL, WALL, WALL, WALL, WALL, WALL}};
    Set<Vector2D> expected = new HashSet<>();
    expected.add(new Vector2D(1, 5));
    expected.add(new Vector2D(2, 0));
    expected.add(new Vector2D(2, 1));
    expected.add(new Vector2D(2, 2));
    expected.add(new Vector2D(2, 3));
    expected.add(new Vector2D(2, 4));
    expected.add(new Vector2D(2, 5));
    expected.add(new Vector2D(3, 0));
    expected.add(new Vector2D(3, 1));
    expected.add(new Vector2D(3, 2));
    expected.add(new Vector2D(3, 3));
    expected.add(new Vector2D(3, 4));
    expected.add(new Vector2D(3, 5));
    expected.add(new Vector2D(4, 0));
    expected.add(new Vector2D(4, 1));
    expected.add(new Vector2D(4, 2));
    expected.add(new Vector2D(4, 3));
    expected.add(new Vector2D(4, 4));
    expected.add(new Vector2D(4, 5));
    expected.add(new Vector2D(5, 5));
    Level<Object> level = new Level<>(area);
    Set<Vector2D> actual = blast.getTemplate(new Vector2D(3, 1), new LevelScanner(level), 5);
    printSight(actual, level);
    assertNotNull(actual);
    assertTrue(actual.containsAll(expected));
    assertEquals(actual.size(), expected.size());
  }

  private void printSight(Set<Vector2D> list, Level level) {
    for (int i = 0; i < level.getLength(); i++) {
      for (int j = 0; j < level.getWidth(); j++) {
        Vector2D location = new Vector2D(i, j);
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
