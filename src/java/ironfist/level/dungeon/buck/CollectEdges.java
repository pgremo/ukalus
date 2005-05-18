package ironfist.level.dungeon.buck;

import ironfist.loop.Level;
import ironfist.math.Vector2D;
import ironfist.util.Closure;

import java.util.List;

public class CollectEdges implements Closure<Vector2D, Object> {

  private static final long serialVersionUID = 1L;
  private Vector2D target;
  private Level level;
  private List<Vector2D> result;

  public CollectEdges(Vector2D target, Level level, List<Vector2D> result) {
    this.target = target;
    this.level = level;
    this.result = result;
  }

  public Object apply(Vector2D direction) {
    Vector2D location = target.add(direction);
    if (level.contains(location) && (Integer) level.get(location) > 0) {
      result.add(location);
    }
    return null;
  }

}
