/*
 * Created on Mar 7, 2005
 *
 */
package ironfist.loop;

/**
 * @author gremopm
 *  
 */
public interface Action {

  Actor getActor();

  int getTurn();

  void perform(Level level);

}