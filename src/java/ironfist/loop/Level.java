/*
 * Created on Feb 16, 2005
 *
 */
package ironfist.loop;

import ironfist.math.Vector;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author gremopm
 *  
 */
public class Level {

  private Queue<Action> queue = new PriorityQueue<Action>(100, new TurnComparator());
  private Object[][] data;

  public Level(Object[][] data) {
    this.data = data;
  }

  public Queue<Action> getQueue(){
    return queue;
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