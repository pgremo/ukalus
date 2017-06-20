/*
 * Created on Apr 24, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ukalus.level.dungeon.recursive;

import ukalus.Tile;
import ukalus.math.Vector2D;

import java.util.function.Function;
import java.util.function.Predicate;

public class MatchesVector implements Predicate<Tile> {

  private Vector2D coordinate;

  public MatchesVector(Vector2D coordinate) {
    this.coordinate = coordinate;
  }

  public boolean test(Tile value) {
    return value.getLocation()
      .equals(coordinate);
  }

}
