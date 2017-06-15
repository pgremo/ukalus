package ukalus.ui.jcurses;

import ukalus.Tile;
import ukalus.TileType;
import ukalus.math.Vector2D;

import java.io.Serializable;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Plan implements Serializable {

  private static final long serialVersionUID = 3689631397870776625L;
  private String name;
  private int height;
  private int width;
  private TileType[][] markers;

  /**
   * Creates a new Map object.
   * 
   * @param height
   *          DOCUMENT ME!
   * @param width
   *          DOCUMENT ME!
   */
  public Plan(String name, int height, int width) {
    this.name = name;
    this.height = height;
    this.width = width;
    markers = new TileType[height][width];
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public int getHeight() {
    return height;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public int getWidth() {
    return width;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param value
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public TileType get(Vector2D value) {
    return markers[value.getX()][value.getY()];
  }

  /**
   * DOCUMENT ME!
   * 
   * @param coordinate
   *          DOCUMENT ME!
   * @param type
   *          DOCUMENT ME!
   */
  public void set(Vector2D coordinate, TileType type) {
    markers[coordinate.getX()][coordinate.getY()] = type;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param value
   *          DOCUMENT ME!
   */
  public void set(Tile value) {
    if (value != null) {
      set(value.getLocation(), value.getTileType());
    }
  }

  /**
   * Returns the name.
   * 
   * @return String
   */
  public String getName() {
    return name;
  }

}