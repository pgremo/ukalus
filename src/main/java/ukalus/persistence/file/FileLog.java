/*
 * Created on May 13, 2004
 *  
 */
package ukalus.persistence.file;

import ukalus.persistence.Log;
import ukalus.persistence.Reference;
import ukalus.persistence.SerializedObjectIterator;
import ukalus.util.Closure;

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

  public void add(Closure o) throws IOException {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    ObjectOutputStream stream = new ObjectOutputStream(buffer);
    stream.writeObject(o);
    byte[] data = buffer.toByteArray();
    channel.seek(channel.length());
    channel.writeInt(data.length);
    channel.write(data);
  }

  public Iterator<Closure<Reference, Object>> iterator() {
    Iterator<Closure<Reference, Object>> result = null;
    try {
      channel.seek(0);
      result = new SerializedObjectIterator<Closure<Reference, Object>>(channel);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return result;
  }

  public void close() throws IOException {
    channel.close();
  }
}