package ironfist.next.actions;

import ironfist.next.Action;
import ironfist.next.ActionType;


/**
 * DOCUMENT ME!
 *
 * @author pmgremo
 */
public class WaitAction implements Action {
  private int time;

  public WaitAction(int time) {
    this.time = time;
  }

  /**
   * @see ironfist.next.Action#tick(float)
   */
  public void tick(int speed) {
    time -= speed;
  }

  /**
   * @see ironfist.next.Action#execute()
   */
  public void act() {
  }

  /**
   * @see ironfist.next.Action#isReady()
   */
  public boolean isReady() {
    return time == 0;
  }

  /**
   * @see ironfist.next.Action#getType()
   */
  public ActionType getType() {
    return null;
  }
}
