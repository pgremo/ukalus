/*
 * Created on May 13, 2004
 *  
 */
package ironfist.persistence.file;

import ironfist.persistence.Command;
import ironfist.persistence.Log;
import ironfist.persistence.SerializedObjectIterator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.Iterator;

/**
 * @author gremopm
 * 
 */
public class FileLog implements Log {

  private RandomAccessFile channel;

  public FileLog(File file) throws FileNotFoundException {
    this.channel = new RandomAccessFile(file, "rw");
  }

  public void clear() throws IOException {
    channel.setLength(0);
  }

  public void add(Command o) throws IOException {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    ObjectOutputStream stream = new ObjectOutputStream(buffer);
    stream.writeObject(o);
    byte[] data = buffer.toByteArray();
    channel.seek(channel.length());
    channel.writeInt(data.length);
    channel.write(data);
  }

  public Iterator<Command> iterator() {
    Iterator<Command> result = null;
    try {
      channel.seek(0);
      result = new SerializedObjectIterator<Command>(channel);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return result;
  }

  public void close() throws IOException {
    channel.close();
  }
}