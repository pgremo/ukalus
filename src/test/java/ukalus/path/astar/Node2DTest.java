/*
 * Created on Feb 11, 2005
 *
 */
package ukalus.path.astar;

import ukalus.level.Level;
import ukalus.math.Vector2D;

import java.util.Collection;

import junit.framework.TestCase;

import static java.lang.Boolean.*;

/**
 * @author gremopm
 * 
 */
public class Node2DTest extends TestCase {

  public void testGetSuccessorsFromMiddle() {
    Level<Boolean> map = new Level<>(new Boolean[][]{
        {TRUE, TRUE, TRUE},
        {TRUE, TRUE, TRUE},
        {TRUE, TRUE, TRUE}});
    Node2D parent = new Node2D(map, Vector2D.Companion.get(1, 0), null);
    Node2D current = new Node2D(map, Vector2D.Companion.get(1, 1), parent);
    Collection<Node> successors = current.getSuccessors();
    assertEquals(3, successors.size());
  }

  public void testGetSuccessorsFromCorner() {
    Level<Boolean> map = new Level<>(new Boolean[][]{
        {TRUE, TRUE, TRUE},
        {TRUE, TRUE, TRUE},
        {TRUE, TRUE, TRUE}});
    Node2D parent = new Node2D(map, Vector2D.Companion.get(1, 0), null);
    Node2D current = new Node2D(map, Vector2D.Companion.get(0, 0), parent);
    Collection<Node> successors = current.getSuccessors();
    assertEquals(1, successors.size());
  }

  public void testGetSuccessorsFromEdge() {
    Level<Boolean> map = new Level<>(new Boolean[][]{
        {TRUE, TRUE, TRUE},
        {TRUE, TRUE, TRUE},
        {TRUE, TRUE, TRUE}});
    Node2D parent = new Node2D(map, Vector2D.Companion.get(0, 0), null);
    Node2D current = new Node2D(map, Vector2D.Companion.get(0, 1), parent);
    Collection<Node> successors = current.getSuccessors();
    assertEquals(2, successors.size());
  }

  public void testGetSuccessorsOriginInMiddle() {
    Level<Boolean> map = new Level<>(new Boolean[][]{
        {TRUE, TRUE, TRUE},
        {TRUE, TRUE, TRUE},
        {TRUE, TRUE, TRUE}});
    Node2D current = new Node2D(map, Vector2D.Companion.get(1, 1), null);
    Collection<Node> successors = current.getSuccessors();
    assertEquals(4, successors.size());
  }
}