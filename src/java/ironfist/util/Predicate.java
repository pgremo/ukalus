package ironfist.util;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public interface Predicate {
  /**
   * DOCUMENT ME!
   *
   * @param value DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  boolean allow(Object value);
}