package ironfist;

import java.util.Random;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public final class Key {

  private static final int MAX_KEYS = 20;
  private static Random random;
  private static Key[] keys;

  static {
    random = new Random();
    keys = new Key[MAX_KEYS];

    for (int index = 0; index < MAX_KEYS; index++) {
      keys[index] = new Key(index);
    }
  }

  private int id;

  private Key(int id) {
    this.id = id;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public static Key random() {
    return keys[random.nextInt(keys.length)];
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public int getId() {
    return id;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param object
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean equals(Object object) {
    Key key = (Key) object;

    return key != null && id == key.id;
  }

  public int hashCode() {
    return id;
  }
}