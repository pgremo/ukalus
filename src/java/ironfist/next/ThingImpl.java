package ironfist.next;

import java.util.HashMap;
import java.util.Map;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class ThingImpl implements Thing {

  private Tile tile;
  private Map properties;
  private Map reactions;
  private Controller controller;
  private Action action;

  {
    reactions = new HashMap();
    properties = new HashMap();
  }

  /**
   * @see ironfist.next.Thing#canPerform(Action)
   */
  public boolean canPerform(Action action) {
    return reactions.containsKey(action.getType());
  }

  /**
   * @see ironfist.next.Thing#perform(Action)
   */
  public void perform(Action action) {
    ((Reaction) reactions.get(action.getType())).react(action);
  }

  /**
   * @see ironfist.next.Thing#getTile()
   */
  public Tile getTile() {
    return tile;
  }

  /**
   * @see ironfist.next.Thing#setTile(Tile)
   */
  public void setTile(Tile tile) {
    this.tile = tile;
  }

  /**
   * @see ironfist.next.Thing#activate()
   */
  public void activate() {
    int speed = ((Integer) ((Node) properties.get(Attribute.SPEED)).getValue()).intValue();
    action.tick(speed);

    while (action.isReady()) {
      action.act();

      if (action == null) {
        controller.determineAction();
      }
    }
  }

  /**
   * Returns the action.
   * 
   * @return Action
   */
  public Action getAction() {
    return action;
  }

  /**
   * Sets the action.
   * 
   * @param action
   *          The action to set
   */
  public void setAction(Action action) {
    this.action = action;
  }

  /**
   * Returns the controller.
   * 
   * @return Controller
   */
  public Controller getController() {
    return controller;
  }

  /**
   * Sets the controller.
   * 
   * @param controller
   *          The controller to set
   */
  public void setController(Controller controller) {
    this.controller = controller;
  }

  /**
   * @see ironfist.next.Thing#getProperty(Object)
   */
  public Object getProperty(Object key) {
    return properties.get(key);
  }

  public void setProperty(Object key, Object value) {
    properties.put(key, value);
  }

  public void addReaction(ActionType type, Reaction reaction) {
    reactions.put(type, reaction);
  }
}