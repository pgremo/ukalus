/*
 * Created on Mar 7, 2005
 *
 */
package ironfist.loop.commands;

import ironfist.loop.Level;
import ironfist.persistence.Command;
import ironfist.persistence.Reference;

/**
 * @author gremopm
 * 
 */
public class Create implements Command {

  private static final long serialVersionUID = 3763094163440613687L;

  public Object execute(Reference reference) {
    reference.set(new Level(new Object[0][0]));
    return null;
  }

}