package ironfist.next;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Cell {

  private static final String ROOT_NAME = "";
  private static final String SEPERATOR = ".";
  private String name;
  private Cell parent;
  private Map children;
  private Object value;

  {
    children = new HashMap();
  }

  /**
   * Returns the name.
   * 
   * @return String
   */
  public String getName() {
    return name;
  }

  public String getAbsoluteName() {
    String result = "";

    if (parent != null) {
      result = parent.getAbsoluteName();
    }

    return result + SEPERATOR + name;
  }

  /**
   * Sets the name.
   * 
   * @param name
   *          The name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the parent.
   * 
   * @return Cell
   */
  public Cell getParent() {
    return parent;
  }

  /**
   * Sets the parent.
   * 
   * @param parent
   *          The parent to set
   */
  public void setParent(Cell parent) {
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
   * @param value
   *          The value to set
   */
  public void setValue(Object value) {
    this.value = value;
  }

  public void put(String key, Object value) {
    getCell(key).setValue(value);
  }

  public void remove(String key) {
    getCell(key).removeCell();
  }

  public Object get(String key) {
    return getCell(key).getValue();
  }

  public void removeCell() {
    value = null;
    parent = null;

    Iterator iterator = children.values()
      .iterator();

    while (iterator.hasNext()) {
      Cell current = (Cell) iterator.next();
      current.removeCell();
    }

    children.clear();
  }

  public Cell getCell(String key) {
    Cell result = null;
    boolean absolute = key.startsWith(SEPERATOR);

    if (absolute) {
      if (parent == null) {
        key = key.substring(1);

        if (ROOT_NAME.equals(key)) {
          result = this;
        } else {
          absolute = false;
        }
      } else {
        result = parent.getCell(key);
      }
    }

    if (!absolute) {
      int boundry = key.indexOf(SEPERATOR);

      if (boundry > 0) {
        String childName = key.substring(1, boundry);
        result = (Cell) children.get(childName);

        if (result == null) {
          result = new Cell();
          result.setName(childName);
          result.setParent(this);
          children.put(childName, result);
        }

        boundry++;

        if (boundry < key.length()) {
          result = result.getCell(key.substring(boundry));
        }
      }
    }

    return result;
  }
}