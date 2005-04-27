/*
 * Created on Feb 16, 2005
 *
 */
package ironfist.level.dungeon.holistic;

/**
 * @author gremopm
 *  
 */
public final class Feature {

  public static final Feature ROOM = new Feature("room");
  public static final Feature PASSAGE = new Feature("passage");

  private String name;

  private Feature(String name) {
    this.name = name;
  }

  public String toString() {
    return name;
  }

}