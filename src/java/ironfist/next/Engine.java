package ironfist.next;

import java.util.Iterator;


/**
 * @author pmgremo
 */
public class Engine {
  private World world;

  public void tick() {
    Iterator iterator = world.getLevel().getThings().iterator();

    while (iterator.hasNext()) {
      ((Thing) iterator.next()).activate();
    }
  }
}
