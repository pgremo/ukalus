package ironfist.util;

import java.util.Arrays;

public class DisjointSet {

  private int[] storage;

  public DisjointSet(int size) {
    storage = new int[size];
    Arrays.fill(storage, -1);
  }

  public void union(int s1, int s2) {
    int root1 = find(s1);
    int root2 = find(s2);
    if (root1 != root2) {
      if (storage[root2] < storage[root1]) {
        storage[root2] += storage[root1];
        storage[root1] = root2;
      } else {
        storage[root1] += storage[root2];
        storage[root2] = root1;
      }
    }
  }

  public int find(int s) {
    int result;
    if (storage[s] < 0) {
      result = s;
    } else {
      storage[s] = find(storage[s]);
      result = storage[s];
    }
    return result;
  }

}
