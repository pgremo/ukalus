package ironfist.next.actions;

import ironfist.next.Action;
import ironfist.next.ActionType;
import ironfist.next.Slot;
import ironfist.next.Thing;


/**
 * DOCUMENT ME!
 *
 * @author pmgremo
 */
public class WearAction implements Action {
  private int time;
  private Thing performer;
  private Thing item;
  private Slot slot;

  public WearAction(int time, Thing performer, Thing item, Slot slot) {
    this.time = time;
    this.performer = performer;
    this.item = item;
    this.slot = slot;
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
    return time <= 0;
  }

  /**
   * @see ironfist.next.Action#getType()
   */
  public ActionType getType() {
    return ActionType.WEAR;
  }

  /**
   * Returns the item.
   *
   * @return Thing
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

  /**
   * Returns the slot.
   *
   * @return Slot
   */
  public Slot getSlot() {
    return slot;
  }
}
