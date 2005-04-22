package ironfist;

import ironfist.math.Vector2D;

import java.io.Serializable;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Tile implements Serializable {

  private static final long serialVersionUID = 3616447882467882032L;
  private Vector2D coordinate;
  private TileType type;

  public Tile(Vector2D coordinate, TileType type) {
    this.coordinate = coordinate;
    this.type = type;
  }

  public Vector2D getLocation() {
    return coordinate;
  }

  public void setLocation(Vector2D coordinate) {
    this.coordinate = coordinate;
  }

  public TileType getTileType() {
    return type;
  }

  public void setTileType(TileType type) {
    this.type = type;
  }

  public String toString() {
    return coordinate + "=" + type;
  }
}