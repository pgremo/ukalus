/*
 * Created on May 13, 2004
 *  
 */
package ukalus.persistence.file;

import ukalus.persistence.Log;
import ukalus.persistence.Reference;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import junit.framework.TestCase;

/**
 * @author gremopm
 *  
 */
public class FileLogTest extends TestCase {

  private File file;

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    file = new File("test.log");
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    file.delete();
  }

  public void testForeach() throws Exception {
    Log log = new FileLog(file);
    Function<Reference,Object> command1 = new MockLogCommand("one");
    Function<Reference,Object> command2 = new MockLogCommand("two");
    log.add(command1);
    log.add(command2);
    log = new FileLog(file);
    Function<Reference,Object> command3 = new MockLogCommand("three");
    log.add(command3);
    List<Function> commands = new ArrayList<Function>();
    Iterator<Function> iterator = log.iterator();
    while (iterator.hasNext()) {
      commands.add(iterator.next());
    }
    assertEquals(Arrays.asList(command1, command2, command3),
      commands);
  }

  public void testClear() throws Exception {
    Log log = new FileLog(file);
    Function<Reference,Object> command1 = new MockLogCommand("one");
    Function<Reference,Object> command2 = new MockLogCommand("two");
    log.add(command1);
    log.add(command2);
    log.clear();
    List<Function> commands = new ArrayList<Function>();
    Iterator<Function> iterator = log.iterator();
    while (iterator.hasNext()) {
      commands.add(iterator.next());
    }
    assertEquals(0, commands.size());
  }
}