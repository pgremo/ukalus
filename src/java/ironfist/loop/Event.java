/*
 * Created on Mar 7, 2005
 *
 */
package ironfist.loop;

import ironfist.level.Level;

import java.io.Serializable;

/**
 * @author gremopm
 * 
 */
public interface Event extends Serializable {

  Object getSource();

  int getTick();

  void process(Level level);

}