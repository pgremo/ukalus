/*
 * Created on Feb 16, 2005
 *
 */
package ironfist.astar;

import ironfist.math.Vector;

/**
 * @author gremopm
 *  
 */
public class Map {

  private Object[][] data;

  public Map(Object[][] data) {
    this.data = data;
  }

  public boolean contains(Vector location) {
    return 0 <= location.getX() && location.getX() < data.length
        && 0 <= location.getY()
        && location.getY() < data[(int) location.getX()].length;
  }

  public Object get(Vector location) {
    return data[(int) location.getX()][(int) location.getY()];
  }

  public void set(Vector location, Object value) {
    data[(int) location.getX()][(int) location.getY()] = value;
  }

  public int getLength() {
    return data.length;
  }

  public int getWidth() {
    return data[0].length;
  }

}