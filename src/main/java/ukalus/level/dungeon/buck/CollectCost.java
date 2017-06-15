package ukalus.level.dungeon.buck;

import ukalus.level.Level;
import ukalus.level.Region;
import ukalus.math.Vector2D;
import ukalus.util.Closure;

import java.util.SortedMap;

public class CollectCost implements Closure<Vector2D, Object> {

  private static final long serialVersionUID = 1L;

  private Region region;
  private Level cells;
  private SortedMap<Integer, Vector2D> map;

  public CollectCost(Region region, Level cells,
      SortedMap<Integer, Vector2D> map) {
    this.region = region;
    this.cells = cells;
    this.map = map;
  }

  public Object apply(Vector2D target) {
    region.setLocation(target);
    map.put(region.cost(cells), target);
    return null;
  }

}
