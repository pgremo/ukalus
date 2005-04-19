/*
 * Created on Feb 16, 2005
 *
 */
package ironfist.loop;

import ironfist.math.Vector2D;

import java.io.Serializable;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author gremopm
 * 
 */
public class Level implements Serializable {

  private static final long serialVersionUID = 3617578201795014705L;
  private Queue<Event> queue = new PriorityQueue<Event>(100,
    new TickComparator());
  private Object[][] data;

  public Level(Object[][] data) {
    this.data = data;
  }

  public Queue<Event> getQueue() {
    return queue;
  }

  public boolean contains(Vector2D location) {
    return 0 <= location.getX() && location.getX() < data.length
        && 0 <= location.getY()
        && location.getY() < data[location.getX()].length;
  }

  public Object get(Vector2D location) {
    return data[location.getX()][location.getY()];
  }

  public void set(Vector2D location, Object value) {
    data[location.getX()][location.getY()] = value;
  }

  public int getLength() {
    return data.length;
  }

  public int getWidth() {
    return data[0].length;
  }

}