package ironfist.util;


public class DisjointSet {

  private Node nodes[];

  public DisjointSet(int size) {
    nodes = new Node[size];
    for (int i = 0; i < size; i++) {
      nodes[i] = new Node(i);
    }
  }

  public int find(int target) {
    int result = target;
    if (nodes[target].parent != target) {
      result = find(nodes[target].parent);
      nodes[target].parent = result;
    }
    return result;
  }

  public int union(int s1, int s2) {
    s1 = find(s1);
    s2 = find(s2);
    if (s1 != s2) {
      if (nodes[s1].size < nodes[s2].size) {
        int temp = s1;
        s1 = s2;
        s2 = temp;
      }
      nodes[s2].parent = s1;
      nodes[s1].size += nodes[s2].size;
    }
    return s1;
  }

  private class Node {

    int parent;
    int size;
    int name;

    public Node(int i) {
      size = 1;
      name = parent = i;
    }

    public String toString() {
      return name + "->" + parent;
    }
  }
}
