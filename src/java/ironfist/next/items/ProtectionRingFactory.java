package ironfist.next.items;

import ironfist.next.ActionType;
import ironfist.next.Attribute;
import ironfist.next.Identifier;
import ironfist.next.Node;
import ironfist.next.Property;
import ironfist.next.Thing;
import ironfist.next.ThingImpl;
import ironfist.next.reactions.WearProtectionRingReaction;


/**
 * DOCUMENT ME!
 *
 * @author pmgremo
 */
public class ProtectionRingFactory {
  private String description;
  private Identifier identifier;

  public ProtectionRingFactory(String description) {
    this.description = description;
    identifier = new ProtectionRingIdentifier(description,
        "ring.protection.identity");
  }

  public Thing create() {
    ThingImpl result = new ThingImpl();
    result.setProperty(Property.IDENTIFIER, identifier);
    result.setProperty(Property.DESCRIPTION, description);

    Node protection = new Node();
    protection.setValue(new Integer(2));
    result.setProperty(Attribute.PROTECTION, protection);

    result.addReaction(ActionType.WEAR, new WearProtectionRingReaction());

    return result;
  }
}
