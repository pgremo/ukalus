/*
 * Created on Apr 24, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ukalus.level.dungeon.recursive;

import ukalus.Tile;
import ukalus.TileType;
import ukalus.util.Closure;

public class IsTileTypeAssignable implements Closure<Tile, Boolean> {

  private static final long serialVersionUID = 3904676072447489846L;
  private Class<? extends TileType> type;

  public IsTileTypeAssignable(Class<? extends TileType> type) {
    this.type = type;
  }

  public Boolean apply(Tile value) {
    return value.getTileType()
      .getClass()
      .isAssignableFrom(type);
  }

}
