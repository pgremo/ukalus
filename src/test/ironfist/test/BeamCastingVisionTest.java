package ironfist.test;

import ironfist.CreateCommand;
import ironfist.Creature;
import ironfist.Referee;

import java.util.Iterator;
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
    List vision = creature.getVision();
    assertNotNull(vision);
    assertTrue(vision.size() > 0);

    Iterator iterator = vision.iterator();

    while (iterator.hasNext()) {
      assertNotNull(iterator.next());
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