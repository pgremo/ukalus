package ironfist.vision;

import ironfist.loop.Level;
import ironfist.math.Vector;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

public class FieldOfVisionTest extends TestCase {

  public void testSeeAll() {
    Object[][] area = new Object[][]{
        {new Object(), new Object(), new Object(), new Object(), new Object()},
        {new Object(), new Object(), new Object(), new Object(), new Object()},
        {new Object(), new Object(), new Object(), new Object(), new Object()},
        {new Object(), new Object(), new Object(), new Object(), new Object()},
        {new Object(), new Object(), new Object(), new Object(), new Object()}};
    Set<Vector> expected = new HashSet<Vector>();
    for (int i = 0; i < area.length; i++) {
      for (int j = 0; j < area[i].length; j++) {
        if (area[i][j] != null) {
          expected.add(new Vector(i, j));
        }
      }
    }
    Level level = new Level(area);
    Set<Vector> actual = new FieldOfVision().lineOfSightPoints(
      new Vector(2, 2), level, 2);
    assertNotNull(actual);
    assertTrue(actual.containsAll(expected));
  }

}
