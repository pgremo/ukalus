/*
 * Created on Feb 28, 2005
 *
 */
package ironfist.loop;

import ironfist.persistence.Reference;
import ironfist.util.Closure;

import java.util.Queue;

/**
 * @author gremopm
 * 
 */
public class Activate implements Closure<Reference, Object> {

  private static final long serialVersionUID = 3257285825019656248L;
  private Event boundary;

  public Activate(Event boundary) {
    this.boundary = boundary;
    if (boundary == null) {
      throw new NullPointerException();
    }
  }

  public Object apply(Reference reference) {
    Level level = (Level) reference.get();
    Queue<Event> queue = level.getQueue();
    queue.add(boundary);
    Event current = null;
    do {
      current = queue.poll();
      current.process(level);
    } while (current != null && boundary.equals(current));
    return null;
  }
}