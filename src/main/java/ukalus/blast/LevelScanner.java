package ukalus.blast;

import ukalus.level.Level;
import ukalus.math.Vector2D;
import java.util.function.Function;

public class LevelScanner implements Function<Vector2D, Boolean> {

  private static final long serialVersionUID = 3833468426717835826L;
  private Level level;

  public LevelScanner(Level level) {
    this.level = level;
  }

  public Boolean apply(Vector2D location) {
    return !level.contains(location) || level.get(location) == null;
  }

}
