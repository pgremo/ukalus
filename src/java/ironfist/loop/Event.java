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

  int getTurn();

  void perform(Level level);

}