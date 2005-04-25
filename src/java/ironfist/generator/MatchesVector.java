/*
 * Created on Apr 24, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ironfist.generator;

import ironfist.Tile;
import ironfist.math.Vector2D;
import ironfist.util.Closure;

public class MatchesVector implements Closure<Tile, Boolean> {

  private static final long serialVersionUID = 3258417244026385720L;

  private Vector2D coordinate;

  public MatchesVector(Vector2D coordinate) {
    this.coordinate = coordinate;
  }

  public Boolean apply(Tile value) {
    return value.getLocation()
      .equals(coordinate);
  }

}
