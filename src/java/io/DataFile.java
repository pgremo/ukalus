package io;

import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class DataFile {

  /*
   * Header: long offset byte free long next free int block size int data size
   */
  private static final int OFFSET = 0;
  private static final int FREE_INDICATOR = 8;
  private static final int NEXT_FREE = 9;
  private static final int BLOCK_SIZE = 17;
  private static final int DATA_SIZE = 21;
  private static final int HEADER_SIZE = 25;
  private static final long FREE_LIST_END = -1;
  private static final byte FREE = 0;
  private static final byte USED = 1;
  private FileChannel channel;
  private long freeListStart;

  public DataFile(FileChannel channel) throws IOException {
    this.channel = channel;

    ByteBuffer data = ByteBuffer.allocate(8);

    if (channel.size() == 0) {
      freeListStart = FREE_LIST_END;
      data.putLong(freeListStart)
        .rewind();
      write(0, data, true);
    } else {
      read(0, data);
      freeListStart = data.getLong();
    }
  }

  /**
   * Marks a block as free
   * 
   * @param offset
   *          DOCUMENT ME!
   * 
   * @throws IOException
   *           DOCUMENT ME!
   */
  public void free(long offset) throws IOException {
    ByteBuffer header = ByteBuffer.allocate(HEADER_SIZE);
    read(offset, header);

    if (header.getLong() != offset) {
      throw new IOException("Corrupt at " + offset);
    }

    header.put(FREE)
      .putLong(freeListStart)
      .rewind();
    write(offset, header, false);

    freeListStart = offset;

    ByteBuffer data = ByteBuffer.allocate(8);
    data.putLong(freeListStart)
      .rewind();
    write(0, data, false);
  }

  public long add(ByteBuffer data) throws IOException {
    long offset = FREE_LIST_END;
    ByteBuffer header = ByteBuffer.allocate(HEADER_SIZE);
    boolean found = false;

    if (freeListStart != FREE_LIST_END) {
      int size = 0;
      long previous = FREE_LIST_END;
      long next = freeListStart;

      do {
        previous = offset;
        offset = next;
        read(offset, header);

        if (header.getLong() != offset) {
          throw new IOException("Corrupt at " + offset);
        }

        if (header.get() != FREE) {
          throw new IOException("Corrupt at " + offset);
        }

        next = header.getLong();
        size = header.getInt();
        found = data.limit() <= size;

        if (found) {
          if (previous == FREE_LIST_END) {
            freeListStart = next;

            ByteBuffer fileHeader = ByteBuffer.allocate(8);
            fileHeader.putLong(freeListStart)
              .rewind();
            write(0, fileHeader, false);
          } else {
            ByteBuffer previousHeader = ByteBuffer.allocate(HEADER_SIZE);
            read(previous, previousHeader);
            previousHeader.putLong(NEXT_FREE, next);
            write(previous, previousHeader, false);
          }
        }
      } while (!found && (next != FREE_LIST_END));
    }

    if (found) {
      header.put(FREE_INDICATOR, USED)
        .putLong(NEXT_FREE, FREE_LIST_END)
        .putInt(DATA_SIZE, data.limit());
    } else {
      offset = channel.size();
      header.rewind();
      header.putLong(offset)
        .put(USED)
        .putLong(FREE_LIST_END)
        .putInt(data.limit())
        .putInt(data.limit());
    }

    header.rewind();
    write(offset, header, !found);
    write(HEADER_SIZE + offset, data, !found);

    return offset;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param offSet
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   * 
   * @throws IOException
   *           DOCUMENT ME!
   */
  public ByteBuffer read(long offset) throws IOException {
    ByteBuffer header = ByteBuffer.allocate(HEADER_SIZE);
    read(offset, header);

    if (header.getLong() != offset) {
      throw new IOException("Corrupt at " + offset);
    }

    if (header.get() == FREE) {
      throw new IOException("Can't read free block");
    }

    ByteBuffer buffer = ByteBuffer.allocate(header.getInt(DATA_SIZE));
    read(HEADER_SIZE + offset, buffer);

    return buffer;
  }

  /**
   * Tries to re-write an existing block.
   * 
   * @param offSet
   *          DOCUMENT ME!
   * @param data
   *          DOCUMENT ME!
   * 
   * @throws IOException
   *           DOCUMENT ME!
   * @throws FileBlockTooShortException
   *           DOCUMENT ME!
   */
  public void update(long offset, ByteBuffer data) throws IOException {
    ByteBuffer header = ByteBuffer.allocate(HEADER_SIZE);
    read(offset, header);

    if (header.getLong() != offset) {
      throw new IOException("Corrupt at " + offset);
    }

    if (header.get() == FREE) {
      throw new IOException("Can't update free block");
    }

    if (header.getInt(BLOCK_SIZE) < data.limit()) {
      throw new FileBlockTooShortException();
    }

    header.putInt(data.limit())
      .rewind();
    write(offset, header, false);
    write(HEADER_SIZE + offset, data, false);
    data.rewind();
  }

  private void write(long offset, ByteBuffer buffer, boolean force)
      throws IOException {
    int total = 0;

    while (buffer.hasRemaining()) {
      total += channel.write(buffer, offset + total);
    }

    channel.force(force);
  }

  private void read(long offset, ByteBuffer buffer) throws IOException {
    int total = 0;

    while (buffer.hasRemaining()) {
      total += channel.read(buffer, offset + total);
    }

    buffer.rewind();
  }
}