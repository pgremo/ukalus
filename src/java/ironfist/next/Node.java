package ironfist.next;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


/**
 * DOCUMENT ME!
 *
 * @author pmgremo
 */
public class Node {
  private Node parent;
  private Collection children;
  private Object value;

  {
    children = new ArrayList();
  }

  /**
   * Returns the parent.
   *
   * @return Node
   */
  public Node getParent() {
    return parent;
  }

  /**
   * Sets the parent.
   *
   * @param parent The parent to set
   */
  public void setParent(Node parent) {
    this.parent = parent;
  }

  /**
   * Returns the value.
   *
   * @return Object
   */
  public Object getValue() {
    return value;
  }

  /**
   * Sets the value.
   *
   * @param value The value to set
   */
  public void setValue(Object value) {
    this.value = value;
  }

  public void addChild(Node child) {
    child.setParent(this);
    children.add(child);
  }

  public void removeChild(Node child) {
    children.remove(child);
    child.setParent(null);
  }

  public Iterator getChildren() {
    return children.iterator();
  }
}
