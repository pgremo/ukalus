package ironfist.persistence;

import ironfist.persistence.file.FileLog;
import ironfist.persistence.file.FileStore;
import ironfist.util.Closure;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;

public final class Persistence {

  private static File dataFile;
  private static File logFile;
  private static Store store;
  private static Log log;
  private static Engine persistence;

  private Persistence() {

  }

  public static void create(String name) throws PersistenceException {
    File directory = new File(System.getProperty("user.home"), "ironfist");
    directory.mkdirs();

    if (directory.listFiles(new Filter(name)).length > 0) {
      throw new PersistenceException("repository " + name + " already exists");
    }

    dataFile = new File(directory, name + ".dat");
    logFile = new File(directory, name + ".log");
    try {
      store = new FileStore(dataFile);
      log = new FileLog(logFile);
      persistence = new Engine(store, log);
      persistence.update(new Create());
      persistence.checkpoint();
    } catch (Exception e) {
      new PersistenceException(e.getMessage());
    }
  }

  public static void open(String name) throws PersistenceException {
    File directory = new File(System.getProperty("user.home"), "ironfist");

    if (directory.listFiles(new Filter(name)).length > 0) {
      throw new PersistenceException("repository " + name + " does not exist");
    }

    dataFile = new File(directory, name + ".dat");
    logFile = new File(directory, name + ".log");
    try {
      store = new FileStore(dataFile);
      log = new FileLog(logFile);
      persistence = new Engine(new FileStore(dataFile), new FileLog(logFile));
    } catch (Exception e) {
      new PersistenceException(e.getMessage());
    }
  }

  public static void put(Object key, Object value) throws PersistenceException {
    try {
      persistence.update(new Put(key, value));
      persistence.checkpoint();
    } catch (IOException e) {
      throw new PersistenceException("error updating root");
    }
  }

  public static Object get(Object key) throws PersistenceException {
    Object result = null;

    try {
      result = persistence.query(new Get(key));
    } catch (Exception e) {
      throw new PersistenceException("error loading object");
    }

    return result;
  }

  public static void close() {
  }

  public static void delete(String name) {
    try {
      persistence.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    File directory = new File(System.getProperty("user.home"), "ironfist");
    File[] files = directory.listFiles(new Filter(name));

    for (int index = 0; index < files.length; index++) {
      files[index].delete();
    }
  }

  public static String[] list() {
    return null;
  }

  private static class Filter implements FilenameFilter {

    private String name;

    public Filter(String name) {
      this.name = name;
    }

    public boolean accept(File dir, String target) {
      return target.startsWith(name + ".");
    }
  }

  private static class Create implements Closure<Reference, Object> {

    private static final long serialVersionUID = 3690476935130198069L;

    public Object apply(Reference reference) {
      reference.set(new HashMap<Object, Object>());
      return null;
    }
  }

  private static class Get implements Closure<Reference, Object> {

    private static final long serialVersionUID = 3258411724993474867L;
    private Object key;

    public Get(Object key) {
      this.key = key;
    }

    public Object apply(Reference reference) {
      return ((HashMap<Object, Object>) reference.get()).get(key);
    }
  }

  private static class Put implements Closure<Reference, Object> {

    private static final long serialVersionUID = 3256727294637979448L;
    private Object key;
    private Object value;

    public Put(Object key, Object value) {
      this.key = key;
      this.value = value;
    }

    public Object apply(Reference reference) {
      return ((HashMap<Object, Object>) reference.get()).put(key, value);
    }
  }

}