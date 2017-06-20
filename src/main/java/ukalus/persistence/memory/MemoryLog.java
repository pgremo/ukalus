package ukalus.persistence.memory;

import ukalus.persistence.Log;
import ukalus.persistence.Reference;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class MemoryLog implements Log {

  private List<Function> storage = new LinkedList<Function>();

  public void clear() throws IOException {
    storage.clear();
  }

  public void add(Function o) throws IOException {
    storage.add(o);
  }

  public void close() throws IOException {
  }

  public Iterator<Function> iterator() {
    return storage.iterator();
  }

}
