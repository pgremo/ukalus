package ironfist.geometry;

import java.io.Serializable;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Vector implements Cloneable, Serializable {

  private double x;
  private double y;

  /**
   * Creates a new Coordinate object.
   * 
   * @param x
   *          DOCUMENT ME!
   * @param y
   *          DOCUMENT ME!
   */
  public Vector(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public double getX() {
    return x;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public double getY() {
    return y;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param value
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Vector add(Vector value) {
    return new Vector(x + value.getX(), y + value.getY());
  }

  /**
   * DOCUMENT ME!
   * 
   * @param value
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Vector subtract(Vector value) {
    return new Vector(x - value.getX(), y - value.getY());
  }

  /**
   * DOCUMENT ME!
   * 
   * @param value
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Vector multiply(double value) {
    return new Vector(x * value, y * value);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param value
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Vector divide(double value) {
    return new Vector(x / value, y / value);
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public double magnitude() {
    return (double) Math.sqrt((x * x) + (y * y));
  }

  /**
   * DOCUMENT ME!
   * 
   * @param value
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public double distance(Vector value) {
    return subtract(value).magnitude();
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Vector normal() {
    return divide(magnitude());
  }

  /**
   * DOCUMENT ME!
   * 
   * @param value
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public double dot(Vector value) {
    return (x * value.getX()) + (y * value.getY());
  }

  /**
   * DOCUMENT ME!
   * 
   * @param value
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public double angle(Vector value) {
    double result = normal().dot(value.normal());

    if (result < -1) {
      result = -1;
    } else if (result > 1) {
      result = 1;
    }

    return Math.acos(result);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param value
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Vector rotate(double value) {
    double r = magnitude();
    double theta = Math.atan2(y, x) - value;

    return new Vector((r * Math.cos(theta)), (r * Math.sin(theta)));
  }

  /**
   * DOCUMENT ME!
   * 
   * @param value
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Vector rotate(Vector value) {
    return new Vector((x * value.getX()) - (y * value.getY()),
      (x * value.getY()) + (y * value.getX()));
  }

  /**
   * DOCUMENT ME!
   * 
   * @param value
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Vector project(Vector value) {
    return multiply(dot(value) / dot(this));
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Vector orthoganal() {
    return new Vector(y, -x);
  }

  /**
   * @see java.lang.Object#equals()
   */
  public boolean equals(Object value) {
    if ((value != null) && value instanceof Vector) {
      return equals((Vector) value);
    }

    return false;
  }

  /**
   * @see java.lang.Object#equals()
   */
  public boolean equals(Vector value) {
    return (x == value.getX()) && (y == value.getY());
  }

  /**
   * @see java.lang.Object#clone()
   */
  public Object clone() {
    Object result = null;

    try {
      result = super.clone();
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e.getMessage());
    }

    return result;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return "[x=" + x + ",y=" + y + "]";
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    long bits = Double.doubleToLongBits(getX());
    bits ^= (Double.doubleToLongBits(getY()) * 31);

    return ((int) bits ^ (int) (bits >> 32));
  }
}