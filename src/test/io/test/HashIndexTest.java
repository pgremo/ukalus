package io.test;

import io.DataFile;
import io.HashIndex;
import io.KeyNotFoundException;

import java.io.File;
import java.io.RandomAccessFile;

import junit.framework.TestCase;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class HashIndexTest extends TestCase {

  private File repos = new File("test.dat");
  private RandomAccessFile raf;

  /**
   * Constructor for HashIndexTest.
   * 
   * @param arg0
   */
  public HashIndexTest(String arg0) {
    super(arg0);
  }

  /**
   * DOCUMENT ME!
   * 
   * @throws Exception
   *           DOCUMENT ME!
   */
  public void testPut() throws Exception {
    long key = 1;
    long offset = 100;
    DataFile fbk = new DataFile(raf.getChannel());
    HashIndex fdx = new HashIndex(fbk, 8);
    fdx.put(key, offset);

    long index = fdx.get(key);
    assertEquals(offset, index);
  }

  public void testUpdate() throws Exception {
    long key = 1;
    long offset = 100;
    DataFile fbk = new DataFile(raf.getChannel());
    HashIndex fdx = new HashIndex(fbk, 8);
    fdx.put(key, offset);

    offset = 200;
    fdx.update(key, offset);
    assertEquals(fdx.get(key), offset);
  }

  public void testFree() throws Exception {
    long key = 1;
    long offset = 100;
    DataFile fbk = new DataFile(raf.getChannel());
    HashIndex fdx = new HashIndex(fbk, 8);
    fdx.put(key, offset);
    fdx.remove(key);

    KeyNotFoundException error = null;

    try {
      fdx.get(key);
    } catch (KeyNotFoundException e) {
      error = e;
    }

    assertNotNull(error);
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