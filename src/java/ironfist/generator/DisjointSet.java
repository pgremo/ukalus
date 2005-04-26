package ironfist.generator;

public class DisjointSet {

  private UnionFindElement elements[];

  public DisjointSet(int size) {
    elements = new UnionFindElement[size];
    for (int i = 0; i < size; i++) {
      elements[i] = new UnionFindElement(i);
    }
  }

  public int find(int target) {
    int result = target;
    if (elements[target].parent != target) {
      result = find(elements[target].parent);
      elements[target].parent = result;
    }
    return result;
  }

  public int union(int s1, int s2) {
    s1 = find(s1);
    s2 = find(s2);
    if (s1 != s2) {
      if (elements[s1].size < elements[s2].size) {
        int temp = s1;
        s1 = s2;
        s2 = temp;
      }
      elements[s2].parent = s1;
      elements[s1].size += elements[s2].size;
    }
    return s1;
  }

  public String toString() {
    int i, j, first;
    int size = elements.length;
    StringBuffer result = new StringBuffer();

    result.append("{UnionFind:");
    for (i = 0; i < size; i++) {
      if (elements[i].parent == i) {
        result.append(" {");
        for (j = 0, first = 1; j < size; j++) {
          if (elements[j].parent == i) {
            if (first == 1)
              result.append(j);
            else
              result.append(" " + j);
            first = 0;
          }
        }
        result.append("}");
      }
    }
    result.append("}");
    return result.toString();
  }

  private class UnionFindElement {

    protected int parent;
    protected int size;
    protected int name;

    public UnionFindElement(int i) {
      size = 1;
      name = parent = i;
    }

    public String toString() {
      return "<UnionFindElement: " + name + " in set " + parent + ">";
    }
  }
}
