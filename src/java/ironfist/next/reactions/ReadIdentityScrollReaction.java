package ironfist.next.reactions;

import ironfist.next.Action;
import ironfist.next.ChoiceCallback;
import ironfist.next.Identifier;
import ironfist.next.Property;
import ironfist.next.Reaction;
import ironfist.next.Thing;
import ironfist.next.actions.ReadAction;

import java.util.Collection;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class ReadIdentityScrollReaction implements Reaction {

  /**
   * @see ironfist.next.Reaction#react(Action)
   */
  public void react(Action action) {
    ReadAction read = (ReadAction) action;
    Thing performer = read.getPerformer();

    Collection inventory = (Collection) performer.getProperty(Property.INVENTORY);

    if (inventory != null) {
      int defaultChoice = -1;
      ChoiceCallback callback = new ChoiceCallback("inventory.select",
        inventory.toArray(), new int[1], false, defaultChoice);

      performer.getController()
        .handleCallback(callback);

      int selected = callback.getSelected()[0];

      if (selected > defaultChoice) {
        Thing item = (Thing) callback.getOptions()[selected];
        Collection identifiers = (Collection) performer.getProperty(Property.IDENTIFIERS);
        identifiers.add((Identifier) item.getProperty(Property.IDENTIFIER));
        inventory.remove(read.getItem());
      }
    }
  }
}