package ironfist;

import java.util.List;


/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public interface Client {
  /**
   * DOCUMENT ME!
   * 
   * @param level DOCUMENT ME!
   */
  void onLevelChange(Level level);

  /**
   * DOCUMENT ME!
   * 
   * @param list DOCUMENT ME!
   */
  void onVisionChange(List list);
}