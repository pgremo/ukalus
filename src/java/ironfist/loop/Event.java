/*
 * Created on Mar 7, 2005
 *
 */
package ironfist.loop;

/**
 * @author gremopm
 *  
 */
public interface Event {

  Object getSource();

  int getTick();

  void process(Level level);

}