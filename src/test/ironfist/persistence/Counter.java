package ironfist.persistence;

import java.io.Serializable;

class Counter implements Serializable {

  private static final long serialVersionUID = 3978708410827288632L;
  private long total = 0;

  long add(long value) {
    return total += value;
  }

  long total() {
    return total;
  }
}