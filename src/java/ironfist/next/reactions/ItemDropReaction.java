package ironfist.next.reactions;

import ironfist.next.Action;
import ironfist.next.Property;
import ironfist.next.Reaction;
import ironfist.next.actions.DropAction;

import java.util.Collection;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class ItemDropReaction implements Reaction {

  /**
   * @see ironfist.next.Reaction#react(Action)
   */
  public void react(Action action) {
    DropAction dropAction = (DropAction) action;
    ((Collection) dropAction.getPerformer()
      .getProperty(Property.INVENTORY)).remove(dropAction.getItem());
  }
}