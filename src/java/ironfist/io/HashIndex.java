package ironfist.io;

import java.io.IOException;

import java.nio.ByteBuffer;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class HashIndex {

  private static final int MAX_BLOCKS = 127;
  private long location;
  private DataFile file;
  private HashIndexBlock[] blocks;

  {
    blocks = new HashIndexBlock[MAX_BLOCKS];
  }

  /**
   * DOCUMENT ME!
   * 
   * @param file
   *          DOCUMENT ME!
   * @param location
   *          DOCUMENT ME!
   * 
   * @throws IOException
   *           DOCUMENT ME!
   */
  public HashIndex(DataFile file, long location) throws IOException {
    this.file = file;
    this.location = location;

    try {
      load();
    } catch (IOException e) {
      for (int i = 0; i < blocks.length; i++) {
        blocks[i] = new HashIndexBlock(this, file, 0);
      }

      store();
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param key
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   * 
   * @throws IOException
   *           DOCUMENT ME!
   */
  public long get(long key) throws IOException {
    return blocks[getHash(key)].get(key);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param key
   *          DOCUMENT ME!
   * @param offset
   *          DOCUMENT ME!
   * 
   * @throws IOException
   *           DOCUMENT ME!
   */
  public void put(long key, long offset) throws IOException {
    blocks[getHash(key)].put(key, offset);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param key
   *          DOCUMENT ME!
   * @param offset
   *          DOCUMENT ME!
   * 
   * @throws IOException
   *           DOCUMENT ME!
   */
  public void update(long key, long offset) throws IOException {
    blocks[getHash(key)].update(key, offset);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param key
   *          DOCUMENT ME!
   * 
   * @throws IOException
   *           DOCUMENT ME!
   */
  public void remove(long key) throws IOException {
    blocks[getHash(key)].remove(key);
  }

  void store() throws IOException {
    ByteBuffer buffer = ByteBuffer.allocate(blocks.length * 8);

    for (int i = 0; i < blocks.length; i++) {
      buffer.putLong(blocks[i].getOffset());
    }

    try {
      file.update(location, buffer);
    } catch (IOException e) {
      long offset = file.add(buffer);

      if (offset != location) {
        throw new IOException("Index could not be created at offset "
            + location + ", " + offset);
      }
    }
  }

  void load() throws IOException {
    ByteBuffer buffer = file.read(location);

    for (int i = 0; i < blocks.length; i++) {
      blocks[i] = new HashIndexBlock(this, file, buffer.getLong());
    }
  }

  private int getHash(long key) {
    return ((int) (key ^ (key >>> 32))) % blocks.length;
  }
}