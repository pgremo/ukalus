package ironfist.next.items;

import ironfist.next.Thing;
import ironfist.next.ThingImpl;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class BattleAxeFactory {

  private static final String DESCRIPTION = "item.weapon.battleaxe";

  public Thing create() {
    Thing result = new ThingImpl();

    return result;
  }
}