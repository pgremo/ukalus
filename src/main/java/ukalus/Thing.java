package ukalus;

import java.io.Serializable;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Thing implements Serializable {

  private static final long serialVersionUID = 3257288011124519993L;
  private Creature owner;

  /**
   * Returns the owner.
   * 
   * @return Creature
   */
  public Creature getOwner() {
    return owner;
  }

  /**
   * Sets the owner.
   * 
   * @param owner
   *          The owner to set
   */
  public void setOwner(Creature owner) {
    this.owner = owner;
  }
}