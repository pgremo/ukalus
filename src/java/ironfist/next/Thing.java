package ironfist.next;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public interface Thing {

  void activate();

  Tile getTile();

  void setTile(Tile tile);

  boolean canPerform(Action action);

  void perform(Action action);

  void setProperty(Object key, Object value);

  Object getProperty(Object key);

  Controller getController();

  void setController(Controller controller);

  void addReaction(ActionType type, Reaction reaction);
}