/*
 * Created on Aug 6, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package next.game.commands;

/**
 * @author a202490
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Direction {

  public static final Direction NORTH = new Direction("north");
  public static final Direction NORTH_EAST = new Direction("north east");
  public static final Direction EAST = new Direction("east");
  public static final Direction SOUTH_EAST = new Direction("south east");
  public static final Direction SOUTH = new Direction("south");
  public static final Direction SOUTH_WEST = new Direction("south west");
  public static final Direction WEST = new Direction("west");
  public static final Direction NORTH_WEST = new Direction("north west");
  public static final Direction UP = new Direction("up");
  public static final Direction DOWN = new Direction("down");
  private String name;

  private Direction(String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return name;
  }
}