package ironfist.ui.jcurses;

import ironfist.Creature;
import ironfist.Referee;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Runner implements Runnable {

  private JCursesClient client;
  private Creature creature;

  /**
   * Creates a new Runner object.
   * 
   * @param client
   *          DOCUMENT ME!
   * @param creature
   *          DOCUMENT ME!
   */
  public Runner(JCursesClient client, Creature creature) {
    this.client = client;
    this.creature = creature;
  }

  /**
   * @see java.lang.Runnable#run()
   */
  public void run() {
    Referee.run(creature);
    client.setGameEnd();
  }

  /**
   * DOCUMENT ME!
   */
  public void stop() {
    Referee.stop();
  }
}