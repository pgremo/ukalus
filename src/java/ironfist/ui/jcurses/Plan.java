package ironfist.ui.jcurses;

import ironfist.Tile;
import ironfist.TileType;
import ironfist.math.Vector;

import java.io.Serializable;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Plan implements Serializable {

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
  public TileType get(Vector value) {
    return markers[(int) value.getX()][(int) value.getY()];
  }

  /**
   * DOCUMENT ME!
   * 
   * @param coordinate
   *          DOCUMENT ME!
   * @param type
   *          DOCUMENT ME!
   */
  public void set(Vector coordinate, TileType type) {
    markers[(int) coordinate.getX()][(int) coordinate.getY()] = type;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param value
   *          DOCUMENT ME!
   */
  public void set(Tile value) {
    if (value != null) {
      set(value.getCoordinate(), value.getTileType());
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