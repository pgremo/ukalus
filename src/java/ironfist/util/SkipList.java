/*
 * Created on Feb 11, 2005
 *  
 */
package ironfist.util;

import java.util.Iterator;

//implement the skip list with an integer key/object
//and implement the hooks to fire changes to the view
public class SkipList {

  private SkipNode head;
  private SkipNode tail;
  private int maxHeight;
  private int curHeight;
  private RandomHeight randGen;

  public SkipList(double p, int m) {
    curHeight = 1;
    maxHeight = m;
    randGen = new RandomHeight(m, p);

    // Create head and tail and attach them
    head = new SkipNode(maxHeight);
    tail = new SkipNode(SkipNode.MaxNodeValue + 1, maxHeight);
    for (int x = 1; x <= maxHeight; x++) {
      head.setFwdNode(x, tail);
    }
  }

  // insert an element
  public boolean insert(int k) {
    int lvl = 0;
    int h = 0;
    SkipNode[] updateVec = new SkipNode[maxHeight + 1];
    SkipNode tmp = head;
    int cmpKey;

    if (k < SkipNode.MinNodeValue) {
      // last update
      return false;
    }

    // Figure out where new node goes
    for (h = curHeight; h >= 1; h--) {
      tmp.setVisited(h, true);

      cmpKey = tmp.getFwdNode(h)
        .getKey();
      while (cmpKey < k) {
        tmp = tmp.getFwdNode(h);

        // print current view
        tmp.setVisited(h, true);

        cmpKey = tmp.getFwdNode(h)
          .getKey();
      }
      updateVec[h] = tmp;
    }
    tmp = tmp.getFwdNode(1);
    cmpKey = tmp.getKey();

    // If dup, return false
    if (cmpKey == k) {
      return false;
    }
    // Perform an insert
    lvl = randGen.newLevel();
    if (lvl > curHeight) {
      for (int i = curHeight + 1; i <= lvl; i++) {
        updateVec[i] = head;
      }
      curHeight = lvl;
    }

    // Insert new element
    tmp = new SkipNode(k, lvl);
    for (int i = 1; i <= lvl; i++) {
      // print current view
      tmp.setVisited(h, true);

      tmp.setFwdNode(i, updateVec[i].getFwdNode(i));
      updateVec[i].setFwdNode(i, tmp);
    }

    // signal new node inserted
    tmp.setVisited(h, true);

    return true;
  }

  public SkipNode getHead() {
    return head;
  }

  public SkipNode getTail() {
    return tail;
  }

  public int getMaxHeight() {
    return maxHeight;
  }

  // execute a find operation
  public int find(int key) {
    int h = 0;
    SkipNode tmp = head;
    int cmpKey;
    int result;
    int findIndex = 1;

    // Find the key and return the node
    for (h = curHeight; h >= 1; h--) {
      // print current view
      tmp.setVisited(h, true);
      // look ahead at current list height to next key
      cmpKey = tmp.getFwdNode(h)
        .getKey();
      // if next key is less than this key then that's too far
      // descend the list of forward nodes to find one that is not too far
      while (cmpKey < key) {

        tmp = tmp.getFwdNode(h);

        // print current view
        tmp.setVisited(h, true);

        // look at next key
        cmpKey = tmp.getFwdNode(h)
          .getKey();
      }
      // stop when keys are equal
      if (cmpKey == key) {
        findIndex = h;
        break;
      }
    }

    tmp = tmp.getFwdNode(findIndex);
    cmpKey = tmp.getKey();

    tmp.setVisited(h, true);
    if (cmpKey == key) {
      result = key;
    } else {
      result = SkipNode.MinNodeValue - 1;
    }
    return result;
  }

  // traverse the skip list at the '1' level as a linked list
  public void traverse(SkipListVisitor v) {
    SkipNode tmp;

    tmp = head;
    while (tmp != tail) {
      // visit the node
      v.visit(tmp);
      // next node
      tmp = tmp.getFwdNode(1);
    }
    v.visit(tail);
  }

  // mark all nodes 'not visited'
  public void clearVisited() {
    SkipNode tmp;

    tmp = head;
    while (tmp.getKey() <= SkipNode.MaxNodeValue) {
      tmp.clearVisited();
      tmp = tmp.getFwdNode(1);
    }
  }

  // implement an enumerator for the skip list
  public Iterator iterator() {
    return new SkipListIterator();
  }

  // traverse at level 1 as a linked list
  class SkipListIterator implements Iterator {

    private SkipNode next;

    public SkipListIterator() {
      next = head.getFwdNode(1);
    }

    public boolean hasNext() {
      boolean result;
      if (next.getKey() <= SkipNode.MaxNodeValue) {
        result = true;
      } else {
        result = false;
      }
      return result;
    }

    public Object next() {
      Integer i;

      i = new Integer(next.getKey());
      next = next.getFwdNode(1);

      return i;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  // test function
  public void dump() {
    traverse(new SkipListPrintVisitor());
  }

  // test function
  class SkipListPrintVisitor implements SkipListVisitor {

    public void visit(SkipNode n) {
      int k;

      // print the Key
      k = n.getKey();

      if (k < 0) {
        System.out.print("h");
      } else {
        System.out.print(n.getKey());
      }
      // print nodes
      for (int i = 0; i < maxHeight; i++) {
        if (i > n.getHgt()) {
          System.out.print(".");
        } else {
          System.out.print("v");
        }
      }
      System.out.println();
    }
  }

  public static void main(String[] args) {
    SkipList sl = new SkipList(0.5, 4);
    sl.insert(1);
    sl.insert(9);
    sl.insert(8);
    sl.insert(3);
    sl.insert(5);
    sl.insert(2);
    sl.insert(4);
    sl.insert(7);
    sl.insert(6);

    sl.dump();

  }
}

