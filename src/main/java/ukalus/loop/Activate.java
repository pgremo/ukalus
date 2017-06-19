/*
 * Created on Feb 28, 2005
 *
 */
package ukalus.loop;

import ukalus.level.Level;
import ukalus.persistence.Reference;
import ukalus.util.Closure;

import java.util.Queue;

/**
 * @author gremopm
 */
public class Activate implements Closure<Reference<Level<Object>>, Object> {

  private static final long serialVersionUID = 3257285825019656248L;
  private Event boundary;

  public Activate(Event boundary) {
    this.boundary = boundary;
    if (boundary == null) {
      throw new NullPointerException();
    }
  }

  public Object apply(Reference<Level<Object>> reference) {
    Level<Object> level = reference.get();
    Queue<Event> queue = level.getQueue();
    queue.add(boundary);
    Event current = queue.poll();
    while (current != null && boundary.equals(current)) {
      current.process(level);
      current = queue.poll();
    }
    return null;
  }
}