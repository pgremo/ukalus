/*
 * Created on May 13, 2004
 *  
 */
package ironfist.persistence;


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

  /*
   * (non-Javadoc)
   * 
   * @see persistence.Log#clear()
   */
  public void clear() throws IOException {
    channel.setLength(0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see persistence.Log#add(java.lang.Object)
   */
  public void add(Object object) throws IOException {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    ObjectOutputStream stream = new ObjectOutputStream(buffer);
    stream.writeObject(object);
    byte[] data = buffer.toByteArray();
    channel.seek(channel.length());
    channel.writeInt(data.length);
    channel.write(data);
  }

  /*
   * (non-Javadoc)
   * 
   * @see persistence.Log#foreach(persistence.Command)
   */
  public Iterator iterator() throws IOException {
    channel.seek(0);
    return new SerializedObjectIterator(channel);
  }
  
  public void close() throws IOException {
    channel.close();
  }
}