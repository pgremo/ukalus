package ironfist;

import ironfist.math.Vector;

import java.io.Serializable;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Tile implements Serializable {

  private static final long serialVersionUID = 3616447882467882032L;
  private Vector coordinate;
  private TileType type;

  /**
   * Creates a new Tile object.
   * 
   * @param coordinate
   *          DOCUMENT ME!
   * @param type
   *          DOCUMENT ME!
   */
  public Tile(Vector coordinate, TileType type) {
    this.coordinate = coordinate;
    this.type = type;
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
   * Returns the type.
   * 
   * @return TileType
   */
  public TileType getTileType() {
    return type;
  }

  /**
   * Sets the type.
   * 
   * @param type
   *          The type to set
   */
  public void setTileType(TileType type) {
    this.type = type;
  }
}