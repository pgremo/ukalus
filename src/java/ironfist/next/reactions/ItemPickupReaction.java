package ironfist.next.reactions;

import ironfist.next.Action;
import ironfist.next.Property;
import ironfist.next.Reaction;
import ironfist.next.actions.PickupAction;

import java.util.Collection;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class ItemPickupReaction implements Reaction {

  /**
   * @see ironfist.next.Reaction#react(Action)
   */
  public void react(Action action) {
    PickupAction pickupAction = (PickupAction) action;
    ((Collection) pickupAction.getPerformer()
      .getProperty(Property.INVENTORY)).add(pickupAction.getItem());
  }
}