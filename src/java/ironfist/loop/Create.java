/*
 * Created on Mar 7, 2005
 *
 */
package ironfist.loop;

import ironfist.persistence.Reference;
import ironfist.util.Closure;

/**
 * @author gremopm
 * 
 */
public class Create implements Closure<Reference, Object> {

  private static final long serialVersionUID = 3763094163440613687L;

  public Object apply(Reference reference) {
    reference.set(new Level(new Object[0][0]));
    return null;
  }

}