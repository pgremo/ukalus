package ukalus.persistence;

import junit.framework.TestCase;
import ukalus.persistence.file.FileLog;
import ukalus.persistence.file.FileStore;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class EngineTest extends TestCase {

  private Engine server;
  private Set<Engine> servers = new HashSet<>();

  public void testServer() throws Exception {
    crashRecover();
    set();
    add(40, 40); // 1
    add(30, 70); // 2
    assertTotal(70);

    crashRecover();
    assertTotal(70);

    add(20, 90); // 3
    add(15, 105); // 4
    server.checkpoint();
    server.checkpoint();
    assertTotal(105);
    throwException();

    crashRecover();
    server.checkpoint();
    add(10, 115); // 5
    server.checkpoint();
    add(5, 120); // 6
    add(4, 124); // 7
    assertTotal(124);

    crashRecover();
    add(3, 127); // 8
    assertTotal(127);

    server.checkpoint();
    clearPersistence();
    server.checkpoint();
    crashRecover();
    assertTotal(127);

    add(2, 129); // 9
    crashRecover();
    assertTotal(129);

  }

  private void set() throws Exception {
    server.update(new Setter(new Counter()));
  }

  private void crashRecover() throws Exception {
    server = new Engine(new FileStore(new File("test")), new FileLog(new File("test.log")));
    servers.add(server);
  }

  private void throwException() throws IOException {
    try {
      server.update(new ThrowException());
    } catch (RuntimeException e) {

    }
  }

  private void add(long value, long expected) throws Exception {
    Long total = (Long) server.update(new Adder(value));
    assertEquals(expected, total.longValue());
  }

  private void assertTotal(long expected) throws Exception {
    Counter system = (Counter) server.update(new Getter());
    assertEquals(expected, system.total());
  }

  protected void tearDown() throws Exception {
    super.tearDown();
    clearPersistence();
  }

  private void clearPersistence() throws Exception {
    Iterator<Engine> iterator = servers.iterator();
    while (iterator.hasNext()) {
      iterator.next()
        .checkpoint();
      iterator.remove();
    }

    File store = new File("test");
    if (store.exists()) {
      store.delete();
    }
    File log = new File("test.log");
    if (log.exists()) {
      log.delete();
    }
  }
}