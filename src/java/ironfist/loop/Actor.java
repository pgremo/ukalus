/*
 * Created on Feb 28, 2005
 *
 */
package ironfist.loop;

import java.io.Serializable;

/**
 * @author gremopm
 * 
 */
public interface Actor extends Serializable {

  void act(Level level);

}