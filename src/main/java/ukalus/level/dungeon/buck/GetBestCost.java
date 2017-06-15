package ukalus.level.dungeon.buck;

import ukalus.util.Closure;

public class GetBestCost implements Closure<Integer, Boolean> {

  private static final long serialVersionUID = 1L;

  public Boolean apply(Integer first) {
    return first > 0 && first < Integer.MAX_VALUE;
  }
}
