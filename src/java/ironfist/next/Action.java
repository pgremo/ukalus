package ironfist.next;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public interface Action {

  void tick(int speed);

  void act();

  boolean isReady();

  ActionType getType();
}