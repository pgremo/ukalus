package ironfist.persistence.memory;

import ironfist.persistence.Log;
import ironfist.persistence.Reference;
import ironfist.util.Closure;

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
