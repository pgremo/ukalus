package ukalus;

import ukalus.math.Vector2D;

import java.io.Serializable;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Stairs implements Portal, Serializable {

  private static final long serialVersionUID = 3833188025416037685L;
  public final static String UP = "up";
  public final static String DOWN = "down";
  private Vector2D coordinate;
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
  public Vector2D getCoordinate() {
    return coordinate;
  }

  /**
   * Sets the coordinate.
   * 
   * @param location
   *          The coordinate to set
   */
  public void setCoordinate(Vector2D coordinate) {
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
   * @param levelName
   *          The levelName to set
   */
  public void setLevelName(String levelName) {
    this.levelName = levelName;
  }
}