package ironfist.next.actions;

import ironfist.next.Action;
import ironfist.next.ActionType;
import ironfist.next.Thing;


/**
 * DOCUMENT ME!
 *
 * @author pmgremo
 */
public class PickupAction implements Action {
  private int time;
  private Thing performer;
  private Thing item;

  public PickupAction(int time, Thing performer, Thing item) {
    this.time = time;
    this.performer = performer;
    this.item = item;
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
    item.perform(this);
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

  /**
   * Returns the item.
   *
   * @return Item
   */
  public Thing getItem() {
    return item;
  }

  /**
   * Returns the performer.
   *
   * @return Thing
   */
  public Thing getPerformer() {
    return performer;
  }
}
