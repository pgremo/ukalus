package ironfist.blast;

import ironfist.math.Vector2D;
import ironfist.util.Closure;

import java.util.Set;

public interface Blast {

  Set<Vector2D> getTemplate(Vector2D location,
      Closure<Vector2D, Boolean> scanner, int radius);

}