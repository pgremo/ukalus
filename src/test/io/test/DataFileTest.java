package io.test;

import io.DataFile;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import java.nio.ByteBuffer;


/**
 * DOCUMENT ME!
 *
 * @author pmgremo
 */
public class DataFileTest extends TestCase {
  private File repos = new File("test.dat");
  private RandomAccessFile raf;

  /**
   * Constructor for FileTest.
   *
   * @param arg0
   */
  public DataFileTest(String arg0) {
    super(arg0);
  }

  public void testAdd() throws Exception {
    String control = "some stuff";
    ByteBuffer data = ByteBuffer.wrap(control.getBytes());
    DataFile fbk = new DataFile(raf.getChannel());
    long offset = fbk.add(data);
    String result = new String(fbk.read(offset).array());
    assertEquals(control, result);
  }

  public void testUpdate() throws Exception {
    String control = "some stuff";
    ByteBuffer data = ByteBuffer.wrap(control.getBytes());
    DataFile fbk = new DataFile(raf.getChannel());
    long offset = fbk.add(data);
    String result = new String(fbk.read(offset).array());
    assertEquals(control, result);

    control = "more stuff";
    data = ByteBuffer.wrap(control.getBytes());
    fbk.update(offset, data);
    result = new String(fbk.read(offset).array());
    assertEquals(control, result);
  }

  public void testFree() throws Exception {
    String control = "some stuff";
    ByteBuffer data = ByteBuffer.wrap(control.getBytes());
    DataFile fbk = new DataFile(raf.getChannel());
    long offset = fbk.add(data);
    fbk.free(offset);

    IOException error = null;

    try {
      fbk.read(offset);
    } catch (IOException e) {
      error = e;
    }

    assertNotNull(error);
  }

  public void testReuse() throws Exception {
    DataFile fbk = new DataFile(raf.getChannel());
    String control = "some stuff";
    ByteBuffer data = ByteBuffer.wrap(control.getBytes());
    long offset = fbk.add(data);
    long size = raf.length();
    fbk.free(offset);

    String stuff = "more stuff";
    data = ByteBuffer.wrap(control.getBytes());
    fbk.add(data);
    assertEquals(size, raf.length());
  }

  /**
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();

    raf.close();
    repos.delete();
  }

  /**
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();

    raf = new RandomAccessFile(repos, "rwd");
  }
}
