package ironfist.generator;

import ironfist.Tile;
import ironfist.util.Closure;

public class TileTypePredicate implements Closure<Tile, Boolean> {

  private static final long serialVersionUID = 3834307341104395829L;
  private Class tileTypeClass;

  public TileTypePredicate(Class tileTypeClass) {
    this.tileTypeClass = tileTypeClass;
  }

  public Boolean apply(Tile value) {
    return value != null && tileTypeClass.equals(value.getTileType()
      .getClass());
  }
}