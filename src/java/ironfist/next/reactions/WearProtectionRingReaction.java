package ironfist.next.reactions;

import ironfist.next.Action;
import ironfist.next.Attribute;
import ironfist.next.Node;
import ironfist.next.Reaction;
import ironfist.next.Thing;
import ironfist.next.actions.WearAction;


/**
 * DOCUMENT ME!
 *
 * @author pmgremo
 */
public class WearProtectionRingReaction implements Reaction {
  /**
   * @see ironfist.next.Reaction#react(Action)
   */
  public void react(Action action) {
    WearAction wear = (WearAction) action;
    Thing performer = wear.getPerformer();
    Thing item = wear.getItem();
    Node protection = (Node) performer.getProperty(Attribute.PROTECTION);
    Node bonus = (Node) item.getProperty(Attribute.PROTECTION);
    protection.addChild(bonus);
    performer.setProperty(wear.getSlot(), item);
  }
}
