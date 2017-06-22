package ukalus;

import java.util.List;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ukalus.persistence.Persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class BeamCastingVisionTest {

  private Creature creature;

  /**
   * DOCUMENT ME!
   */
  @Test
  public void testVision() {
    List<Tile> vision = creature.getVision();
    assertNotNull(vision);
    assertTrue(vision.size() > 0);
    for (Tile current : vision) {
      assertNotNull(current);
    }
  }

  /**
   * @see junit.framework.TestCase#setUp()
   */
  @Before
  public void setUp() throws Exception {
    creature = (Creature) new CreateCommand().execute("ja");
  }

  /**
   * @see junit.framework.TestCase#tearDown()
   */
  @After
  public void tearDown() throws Exception {
    Persistence.INSTANCE.delete("ja");
    Referee.quit(creature);
  }
}