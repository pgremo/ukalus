/*
 * Created on Feb 28, 2005
 *
 */
package ironfist.loop.commands;

import ironfist.loop.Actor;
import ironfist.loop.Event;
import ironfist.loop.Level;
import ironfist.loop.events.ReadyToAct;
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

  public Object execute(Reference reference) {
    Level level = (Level) reference.get();
    Queue<Event> queue = level.getQueue();
    Event current = queue.poll();
    if (current != null) {
      do {
        current.process(level);
        current = queue.poll();
      } while (current != null
          && !(current instanceof ReadyToAct && current.getSource()
            .equals(hero)));
    }
    return null;
  }
}