package ironfist.persistence.memory;

import ironfist.persistence.Reference;
import ironfist.persistence.Store;

import java.io.IOException;
import java.io.Serializable;

public class MemoryStore implements Store {

  private Reference reference = new Reference();

  public void store(Serializable object) throws IOException {
    reference.set(object);
  }

  public Serializable load() throws IOException, ClassNotFoundException {
    return reference.get();
  }

  public void close() throws IOException {
  }

}
