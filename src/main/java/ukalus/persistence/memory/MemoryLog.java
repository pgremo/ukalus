package ukalus.persistence.memory;

import ukalus.persistence.Log;
import ukalus.persistence.Reference;
import ukalus.util.Closure;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MemoryLog implements Log {

  private List<Closure<Reference, Object>> storage = new LinkedList<Closure<Reference, Object>>();

  public void clear() throws IOException {
    storage.clear();
  }

  public void add(Closure<Reference, Object> o) throws IOException {
    storage.add(o);
  }

  public void close() throws IOException {
  }

  public Iterator<Closure<Reference, Object>> iterator() {
    return storage.iterator();
  }

}
