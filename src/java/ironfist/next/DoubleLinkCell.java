package ironfist.next;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class DoubleLinkCell {

  private DoubleLinkCell next;
  private DoubleLinkCell previous;

  /**
   * Returns the next.
   * 
   * @return DoubleLinkCell
   */
  public DoubleLinkCell getNext() {
    return next;
  }

  /**
   * Returns the previous.
   * 
   * @return DoubleLinkCell
   */
  public DoubleLinkCell getPrevious() {
    return previous;
  }

  /**
   * Sets the next.
   * 
   * @param next
   *          The next to set
   */
  public void setNext(DoubleLinkCell next) {
    this.next = next;
  }

  /**
   * Sets the previous.
   * 
   * @param previous
   *          The previous to set
   */
  public void setPrevious(DoubleLinkCell previous) {
    this.previous = previous;
  }

  public DoubleLinkCell remove() {
    DoubleLinkCell result = next;
    remove(this, this);

    return result;
  }

  public void remove(DoubleLinkCell begin, DoubleLinkCell end) {
    if (end.next != null) {
      end.next.previous = begin.previous;
    }

    if (begin.previous != null) {
      begin.previous.next = end.next;
    }

    begin.previous = null;
    end.next = null;
  }

  public void append(DoubleLinkCell cell) {
    DoubleLinkCell begin = cell;
    DoubleLinkCell end = begin;

    while (end.next != null) {
      end = end.next;
    }

    end.next = next;
    begin.previous = this;
  }
}