package ukalus.persistence.memory;

import ukalus.persistence.Reference;
import ukalus.persistence.Store;

import java.io.IOException;
import java.io.Serializable;

public class MemoryStore<T extends Serializable> implements Store<T> {

  private Reference<T> reference = new Reference<>();

  public void store(T object) throws IOException {
    reference.set(object);
  }

  public T load() throws IOException, ClassNotFoundException {
    return reference.get();
  }

  public void close() throws IOException {
  }

}
