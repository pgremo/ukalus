/*
 * Created on Mar 8, 2005
 *
 */
package ironfist.loop.commands;

import ironfist.loop.Action;
import ironfist.loop.Level;
import ironfist.persistence.Command;
import ironfist.persistence.Reference;

/**
 * @author gremopm
 * 
 */
public class AddAction implements Command {

  private static final long serialVersionUID = 3835157263710304313L;
  private Action action;

  public AddAction(Action action) {
    this.action = action;
  }

  public Object execute(Object object) {
    ((Level) ((Reference) object).get()).getQueue()
      .add(action);
    return null;
  }

}