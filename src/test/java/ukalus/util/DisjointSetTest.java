package ukalus.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

public class DisjointSetTest extends TestCase {

  private static final int ELEMENTS = 128;
  private static final int COUNT_IN_SET = 16;

  public void testOperations() {
    DisjointSet s = new DisjointSet(ELEMENTS);

    for (int k = 1; k < COUNT_IN_SET; k *= 2) {
      for (int j = 0; j + k < ELEMENTS; j += 2 * k) {
        s.union(s.find(j), s.find(j + k));
      }
    }

    List<Integer> actual = new LinkedList<Integer>();
    int item = 0;
    for (int i = 0; i < ELEMENTS; i++) {
      actual.add(s.find(i));
      item = s.find(i);
      if (i % COUNT_IN_SET == COUNT_IN_SET - 1) {
        assertEquals(Collections.nCopies(COUNT_IN_SET, item), actual);
        actual.clear();
      }
    }
  }
}
