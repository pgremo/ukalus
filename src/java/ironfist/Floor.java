package ironfist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Floor implements TileType, Serializable {

  private static final long serialVersionUID = 3546927995609625650L;
  private Door door;
  private Creature creature;
  private Collection<Thing> things;
  private Portal portal;

  {
    things = new ArrayList<Thing>();
  }

  /**
   * Returns the creature.
   * 
   * @return Creature
   */
  public Creature getCreature() {
    return creature;
  }

  /**
   * Sets the creature.
   * 
   * @param creature
   *          The creature to set
   */
  public void setCreature(Creature creature) {
    this.creature = creature;
  }

  /**
   * Adds a thing
   * 
   * @param value
   *          The thing to add
   */
  public void addThing(Thing value) {
    things.add(value);
  }

  /**
   * Removes a thing
   * 
   * @param value
   *          The thing to remove
   */
  public void removeThing(Thing value) {
    things.remove(value);
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Iterator<Thing> getThings() {
    return things.iterator();
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public int getThingCount() {
    return things.size();
  }

  /**
   * Returns the connector.
   * 
   * @return Connector
   */
  public Portal getPortal() {
    return portal;
  }

  /**
   * Sets the connector.
   * 
   * @param value
   *          The connector to set
   */
  public void setPortal(Portal value) {
    this.portal = value;
  }

  /**
   * Returns the door.
   * 
   * @return Door
   */
  public Door getDoor() {
    return door;
  }

  /**
   * Sets the door.
   * 
   * @param value
   *          The door to set
   */
  public void setDoor(Door value) {
    this.door = value;
  }
  
  public String toString(){
    return "Floor";
  }
}