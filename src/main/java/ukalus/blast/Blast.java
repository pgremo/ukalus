package ukalus.blast;

import ukalus.math.Vector2D;

import java.util.Set;
import java.util.function.Function;

public interface Blast {

  Set<Vector2D> getTemplate(Vector2D location,
                            Function<Vector2D,Boolean> scanner, int radius);

}