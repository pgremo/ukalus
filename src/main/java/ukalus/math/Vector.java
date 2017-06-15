package ukalus.math;

import java.io.Serializable;

/**
 * 
 * @author pmgremo
 */
public class Vector implements Cloneable, Serializable {

  private static final long serialVersionUID = 3258410646906155832L;
  private double x;
  private double y;

  /**
   * Creates a new Coordinate object.
   * 
   * @param x
   *          coordinate
   * @param y
   *          coordinate
   */
  public Vector(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * return the x coordinate
   * 
   * @return x coordinate
   */
  public double getX() {
    return x;
  }

  /**
   * return the y coordinate
   * 
   * @return y coordinate
   */
  public double getY() {
    return y;
  }

  /**
   * add a vector to this and return the sum
   * 
   * @param value
   *          vector to add
   * 
   * @return Vector that is the sum
   */
  public Vector add(Vector value) {
    return new Vector(x + value.getX(), y + value.getY());
  }

  /**
   * subtract a vector from this and return the difference
   * 
   * @param value
   *          vector to subtract
   * 
   * @return Vector that is the difference
   */
  public Vector subtract(Vector value) {
    return new Vector(x - value.getX(), y - value.getY());
  }

  /**
   * multiply a vector to this and return the product
   * 
   * @param value
   *          Vector to multiply
   * 
   * @return Vector that is the product
   */
  public Vector multiply(double value) {
    return new Vector(x * value, y * value);
  }

  /**
   * divide a vector by this and return the quotient
   * 
   * @param value
   *          Vector to divide by
   * 
   * @return Vector that is the quotient
   */
  public Vector divide(double value) {
    return new Vector(x / value, y / value);
  }

  /**
   * return the length of this vector Math.sqrt((x * x) + (y * y))
   * 
   * @return length of this vector
   */
  public double magnitude() {
    return Math.sqrt((x * x) + (y * y));
  }

  /**
   * calculate the distance from this vector to the given vector
   * 
   * @param destination
   * 
   * @return distance
   */
  public double distance(Vector destination) {
    return subtract(destination).magnitude();
  }

  /**
   * calculate the vector normal for this vector divide(magnitude())
   * 
   * @return the vector normal
   */
  public Vector normal() {
    return divide(magnitude());
  }

  /**
   * calculate the dot product for the given vector and this (x * value.getX()) +
   * (y * value.getY())
   * 
   * @param value
   *          to evaluate
   * 
   * @return dot product
   */
  public double dot(Vector value) {
    return (x * value.getX()) + (y * value.getY());
  }

  /**
   * Calculate the angle between a vector and this. Basically:
   * Math.acos(normal().dot(value.normal()))
   * 
   * @param value
   *          other vector
   * 
   * @return angle
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
   * determine the vector of this rotated a given number of degrees
   * 
   * @param value
   *          degrees to rotate
   * 
   * @return new vector
   */
  public Vector rotate(double value) {
    double phi = Math.toRadians(value);
    double cos = Math.cos(phi);
    double sin = Math.sin(phi);
    return new Vector(x * cos + y * sin, y * cos - x * sin);
  }

  /**
   * rotate a vector based on a given vector
   * 
   * @param vector
   *          to rotate by
   * 
   * @return new vector
   */
  public Vector rotate(Vector vector) {
    return new Vector((x * vector.getX()) - (y * vector.getY()),
      (x * vector.getY()) + (y * vector.getX()));
  }

  /**
   * project this vector along given vector.
   * 
   * @param value
   *          to project along
   * 
   * @return new vector
   */
  public Vector project(Vector value) {
    return multiply(dot(value) / dot(this));
  }

  /**
   * return a vector orthoganal to this one Vector(y, -x)
   * 
   * @return new vector
   */
  public Vector orthoganal() {
    return new Vector(y, -x);
  }

  /**
   * @see java.lang.Object#equals()
   */
  public boolean equals(Object value) {
    return value != null && value instanceof Vector
        && x == ((Vector) value).getX() && y == ((Vector) value).getY();
  }

  /**
   * @see java.lang.Object#clone()
   */
  public Object clone() {
    return new Vector(x, y);
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return "(x=" + x + ",y=" + y + ")";
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    long bits = Double.doubleToLongBits(getX());
    bits ^= Double.doubleToLongBits(getY()) * 31;

    return (int) bits ^ (int) (bits >> 32);
  }
}