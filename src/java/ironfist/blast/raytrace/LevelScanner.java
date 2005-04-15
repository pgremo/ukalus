package ironfist.blast.raytrace;

import ironfist.loop.Level;
import ironfist.math.Vector;
import ironfist.util.Closure;

public class LevelScanner implements Closure<Vector, Boolean> {

  private static final long serialVersionUID = 3833468426717835826L;
  private Level level;

  public LevelScanner(Level level) {
    this.level = level;
  }

  public Boolean apply(Vector location) {
    return !level.contains(location) || level.get(location) == null;
  }

}
