package ironfist.next.reactions.test;

import ironfist.next.Action;
import ironfist.next.ActionType;
import ironfist.next.Callback;
import ironfist.next.ChoiceCallback;
import ironfist.next.Controller;
import ironfist.next.Property;
import ironfist.next.Thing;
import ironfist.next.ThingImpl;
import ironfist.next.actions.ReadAction;
import ironfist.next.items.ProtectionRingFactory;
import ironfist.next.reactions.ReadIdentityScrollReaction;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;


/**
 * DOCUMENT ME!
 *
 * @author pmgremo
 */
public class ReadIdentityScrollReactionTest extends TestCase {
  /**
   * Constructor for ReadIdentityScrollReactionTest.
   *
   * @param arg0
   */
  public ReadIdentityScrollReactionTest(String arg0) {
    super(arg0);
  }

  public void testRead() throws Exception {
    Thing performer = new ThingImpl();
    Collection identifiers = new ArrayList();
    performer.setProperty(Property.IDENTIFIERS, identifiers);
    performer.setController(new ControllerImpl());

    Collection inventory = new ArrayList();
    performer.setProperty(Property.INVENTORY, inventory);

    Thing scroll = new ThingImpl();
    scroll.addReaction(ActionType.READ, new ReadIdentityScrollReaction());
    inventory.add(scroll);

    Thing ring = new ProtectionRingFactory("description.ring.0").create();
    inventory.add(ring);

    Action read = new ReadAction(0, performer, scroll);
    read.act();
    assertEquals(identifiers.size(), 1);
  }

  private class ControllerImpl implements Controller {
    /**
     * @see ironfist.next.Controller#determineAction()
     */
    public void determineAction() {
    }

    /**
     * @see ironfist.next.Controller#handleCallback(Callback)
     */
    public void handleCallback(Callback callback) {
      if (callback instanceof ChoiceCallback) {
        ChoiceCallback choice = (ChoiceCallback) callback;

        if (choice.getPrompt()
                    .equals("inventory.select")) {
          Object[] items = choice.getOptions();

          for (int index = 0; index < items.length; index++) {
            if ("description.ring.0".equals(((Thing) items[index]).getProperty(
                    Property.DESCRIPTION))) {
              choice.setSelected(index);

              break;
            }
          }
        }
      }
    }
  }
}
