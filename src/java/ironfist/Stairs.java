package ironfist;

import ironfist.geometry.Vector;

import java.io.Serializable;


/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Stairs implements Portal, Serializable {
  public final static String UP = "up";
  public final static String DOWN = "down";
  private Vector coordinate;
  private String direction;
  private String levelName;

  /**
   * Constructor for Stairs.
   * 
   * @param direction
   */
  public Stairs(String direction) {
    this.direction = direction;
  }

  /**
   * Returns the coordinate.
   * 
   * @return Coordinate
   */
  public Vector getCoordinate() {
    return coordinate;
  }

  /**
   * Sets the coordinate.
   * 
   * @param location The coordinate to set
   */
  public void setCoordinate(Vector coordinate) {
    this.coordinate = coordinate;
  }

  /**
   * Returns the direction.
   * 
   * @return String
   */
  public String getDirection() {
    return direction;
  }

  /**
   * Returns the levelName.
   * 
   * @return String
   */
  public String getLevelName() {
    return levelName;
  }

  /**
   * Sets the levelName.
   * 
   * @param levelName The levelName to set
   */
  public void setLevelName(String levelName) {
    this.levelName = levelName;
  }
}