/*
 * Created on Feb 28, 2005
 *
 */
package ironfist.loop.commands;

import ironfist.loop.Action;
import ironfist.loop.Actor;
import ironfist.loop.Level;
import ironfist.persistence.Command;
import ironfist.persistence.Reference;

import java.util.Queue;

/**
 * @author gremopm
 * 
 */
public class ActivateWorld implements Command {

  private static final long serialVersionUID = 3257285825019656248L;
  private Actor hero;

  public ActivateWorld(Actor hero) {
    this.hero = hero;
  }

  public Object execute(Object object) {
    Level level = (Level) ((Reference) object).get();
    Queue<Action> queue = level.getQueue();
    Action current = queue.poll();
    if (current != null) {
      do {
        current.perform(level);
        current = queue.poll();
      } while (current != null && !current.getActor()
        .equals(hero));
    }
    return null;
  }
}