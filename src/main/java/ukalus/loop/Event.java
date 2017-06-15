/*
 * Created on Mar 7, 2005
 *
 */
package ukalus.loop;

import ukalus.level.Level;

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