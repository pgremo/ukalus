package ironfist.next.items;

import ironfist.next.Attribute;
import ironfist.next.Identifier;
import ironfist.next.Property;
import ironfist.next.Thing;


/**
 * DOCUMENT ME!
 *
 * @author pmgremo
 */
public class ProtectionRingIdentifier implements Identifier {
  private String description;
  private String identity;

  public ProtectionRingIdentifier(String description, String identity) {
    this.description = description;
    this.identity = identity;
  }

  /**
   * @see ironfist.next.Identity#identify(Thing)
   */
  public Object[] identify(Thing thing) {
    Object[] result = null;

    if (description.equals(thing.getProperty(Property.DESCRIPTION))) {
      result = new Object[2];
      result[0] = identity;
      result[1] = thing.getProperty(Attribute.PROTECTION);
    }

    return result;
  }

  /**
   * Returns the description.
   *
   * @return String
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns the identity.
   *
   * @return String
   */
  public String getIdentity() {
    return identity;
  }
}
