/*
 * Created on Feb 28, 2005
 *
 */
package ironfist.loop.commands;

import ironfist.loop.Action;
import ironfist.loop.Actor;
import ironfist.loop.Event;
import ironfist.loop.Level;
import ironfist.persistence.Command;
import ironfist.persistence.Reference;

import java.util.Queue;

/**
 * @author gremopm
 * 
 */
public class ActivateLoop implements Command {

  private static final long serialVersionUID = 3257285825019656248L;
  private Actor hero;

  public ActivateLoop(Actor hero) {
    this.hero = hero;
  }

  public Object execute(Object object) {
    Level level = (Level) ((Reference) object).get();
    Queue<Event> queue = level.getQueue();
    Event current = queue.poll();
    if (current != null) {
      do {
        current.perform(level);
        current = queue.poll();
      } while (current != null && current instanceof Action
          && !current.getSource()
            .equals(hero));
    }
    return null;
  }
}