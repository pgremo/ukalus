package ironfist.persistence.memory;

import ironfist.persistence.Command;
import ironfist.persistence.Log;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MemoryLog implements Log {

  private List<Command> storage = new LinkedList<Command>();

  public void clear() throws IOException {
    storage.clear();
  }

  public void add(Command o) throws IOException {
    storage.add(o);
  }

  public void close() throws IOException {
  }

  public Iterator<Command> iterator() {
    return storage.iterator();
  }

}
