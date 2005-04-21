package ironfist;

import java.util.List;

import junit.framework.TestCase;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class BeamCastingVisionTest extends TestCase {

  private Creature creature;

  /**
   * Constructor for BeamCastingVisionTest.
   * 
   * @param arg0
   */
  public BeamCastingVisionTest(String arg0) {
    super(arg0);
  }

  /**
   * DOCUMENT ME!
   */
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
  protected void setUp() throws Exception {
    super.setUp();

    creature = (Creature) (new CreateCommand().execute("ja"));
  }

  /**
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    Referee.quit(creature);
  }
}