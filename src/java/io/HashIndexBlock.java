package io;

import java.io.IOException;
import java.nio.ByteBuffer;


/**
 * DOCUMENT ME!
 *
 * @author pmgremo
 */
public class HashIndexBlock {
  private static final int KEY_ALLOC = 64;
  private static final int KEY_EXTEND = 16;
  private static final int COUNT_POSITION = 4;
  private long offset;
  private HashIndex index;
  private DataFile file;

  /**
   * Creates a new IndexBlock object.
   *
   * @param index DOCUMENT ME!
   * @param file DOCUMENT ME!
   * @param offset DOCUMENT ME!
   */
  public HashIndexBlock(HashIndex index, DataFile file, long offset) {
    this.index = index;
    this.file = file;
    this.offset = offset;
  }

  /**
   * DOCUMENT ME!
   *
   * @param key DOCUMENT ME!
   * @param value DOCUMENT ME!
   *
   * @throws IOException DOCUMENT ME!
   */
  public void put(long key, long value) throws IOException {
    if (key == 0) {
      throw new IOException("invalid key " + key);
    }

    ByteBuffer buffer = null;

    if (offset == 0) {
      buffer = ByteBuffer.allocate((KEY_ALLOC * 8 * 2) + (2 * 4));
      buffer.putInt(KEY_ALLOC)
            .rewind();
    } else {
      buffer = file.read(offset);
    }

    int keyAlloc = buffer.getInt();
    int keyCount = buffer.getInt();

    if (keyCount == keyAlloc) {
      keyAlloc += KEY_EXTEND;

      ByteBuffer newBuffer = ByteBuffer.allocate((keyAlloc * 8 * 2) + (2 * 4));
      newBuffer.putInt(keyAlloc)
               .putInt(keyCount)
               .put(buffer);
      buffer = newBuffer;
    }

    buffer.position(COUNT_POSITION);
    buffer.putInt(keyCount + 1)
          .asLongBuffer()
          .put(keyCount * 2, key)
          .put((keyCount * 2) + 1, value);
    buffer.rewind();

    boolean append = (offset == 0);

    if (!append) {
      try {
        file.update(offset, buffer);
      } catch (FileBlockTooShortException ex) {
        append = true;
      }
    }

    if (append) {
      long oldOffset = offset;
      offset = file.add(buffer);
      index.store();

      if (oldOffset > 0) {
        file.free(oldOffset);
      }
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @param key DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   *
   * @throws IOException DOCUMENT ME!
   * @throws KeyNotFoundException DOCUMENT ME!
   */
  public long get(long key) throws IOException {
    if (key == 0) {
      throw new IOException("invalid key " + key);
    }

    long result = -1;

    if (offset > 0) {
      ByteBuffer buffer = file.read(offset);
      buffer.position(COUNT_POSITION);

      for (int keyCount = buffer.getInt(); (keyCount > 0) && (result == -1);
          keyCount--) {
        if (buffer.getLong() == key) {
          result = buffer.getLong();
        }

        buffer.getLong();
      }
    }

    if (result == -1) {
      throw new KeyNotFoundException();
    }

    return result;
  }

  /**
   * DOCUMENT ME!
   *
   * @param key DOCUMENT ME!
   * @param newOffset DOCUMENT ME!
   *
   * @throws IOException DOCUMENT ME!
   * @throws KeyNotFoundException DOCUMENT ME!
   */
  public void update(long key, long newOffset) throws IOException {
    if (key == 0) {
      throw new IOException("invalid key " + key);
    }

    boolean found = false;

    if (offset > 0) {
      ByteBuffer buffer = file.read(offset);
      buffer.position(COUNT_POSITION);

      for (int keyCount = buffer.getInt(); (keyCount > 0) && !found;
          keyCount--) {
        long id = buffer.getLong();

        if (id == key) {
          buffer.putLong(newOffset)
                .rewind();
          file.update(offset, buffer);
          found = true;
        } else {
          buffer.getLong();
        }
      }
    }

    if (!found) {
      throw new KeyNotFoundException();
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @param key DOCUMENT ME!
   *
   * @throws IOException DOCUMENT ME!
   * @throws KeyNotFoundException DOCUMENT ME!
   */
  public void remove(long key) throws IOException {
    if (key == 0) {
      throw new IOException("invalid key " + key);
    }

    boolean found = false;

    if (offset > 0) {
      ByteBuffer buffer = file.read(offset);
      buffer.position(COUNT_POSITION);

      int keyCount = buffer.getInt();

      for (int index = keyCount; (index > 0) && !found; index--) {
        buffer.mark();

        long id = buffer.getLong();
        buffer.getLong();

        if (id == key) {
          ByteBuffer remaining = buffer.slice();
          buffer.reset();
          buffer.put(remaining)
                .position(COUNT_POSITION);
          buffer.putInt(keyCount - 1)
                .rewind();
          file.update(offset, buffer);
          found = true;
        }
      }
    }

    if (!found) {
      throw new KeyNotFoundException();
    }
  }

  /**
   * Returns the offset.
   *
   * @return long
   */
  public long getOffset() {
    return offset;
  }
}
