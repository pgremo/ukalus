package ironfist.blast;

import ironfist.level.Level;
import ironfist.math.Vector2D;
import ironfist.util.Closure;

public class LevelScanner implements Closure<Vector2D, Boolean> {

  private static final long serialVersionUID = 3833468426717835826L;
  private Level level;

  public LevelScanner(Level level) {
    this.level = level;
  }

  public Boolean apply(Vector2D location) {
    return !level.contains(location) || level.get(location) == null;
  }

}
