/*
 * Created on Mar 8, 2005
 *
 */
package ironfist.loop.commands;

import ironfist.loop.Event;
import ironfist.loop.Level;
import ironfist.persistence.Command;
import ironfist.persistence.Reference;

/**
 * @author gremopm
 * 
 */
public class AddEvent implements Command {

  private static final long serialVersionUID = 3835157263710304313L;
  private Event action;

  public AddEvent(Event action) {
    this.action = action;
  }

  public Object execute(Reference reference) {
    ((Level) reference.get()).getQueue()
      .add(action);
    return null;
  }

}