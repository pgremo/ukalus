package ironfist.persistence;

import jdbm.btree.BTree;

import jdbm.helper.MRU;
import jdbm.helper.ObjectCache;
import jdbm.helper.StringComparator;

import jdbm.recman.RecordManager;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class Persistence {

  private static RecordManager recman;
  private static ObjectCache cache;
  private static BTree tree;

  public static void create(String name) throws PersistenceException {
    File directory = new File(System.getProperty("user.home"), "ironfist");
    directory.mkdirs();

    if (directory.listFiles(new Filter(name)).length > 0) {
      throw new PersistenceException("repository " + name + " already exists");
    }

    File target = new File(directory, name);

    try {
      recman = new RecordManager(target.getAbsolutePath());
      cache = new ObjectCache(recman, new MRU(100));

      tree = new BTree(recman, cache, new StringComparator());
    } catch (IOException e) {
      throw new PersistenceException(e.getMessage());
    }
  }

  public static void open(String name) throws PersistenceException {
    File directory = new File(System.getProperty("user.home"), "ironfist");

    if (directory.listFiles(new Filter(name)).length > 0) {
      throw new PersistenceException("repository " + name + " does not exist");
    }

    File target = new File(directory, name);

    try {
      recman = new RecordManager(target.getAbsolutePath());
      cache = new ObjectCache(recman, new MRU(100));

      tree = new BTree(recman, cache, new StringComparator());
    } catch (IOException e) {
      throw new PersistenceException(e.getMessage());
    }
  }

  public static void put(Object key, Object value) throws PersistenceException {
    try {
      tree.insert(key, value, false);
      recman.commit();
    } catch (IOException e) {
      throw new PersistenceException("error updating root");
    }
  }

  public static Object get(Object key) throws PersistenceException {
    Object result = null;

    try {
      result = tree.find(key);
    } catch (Exception e) {
      throw new PersistenceException("error loading object");
    }

    return result;
  }

  public static void close() {
    try {
      recman.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void delete(String name) {
    try {
      recman.close();
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
    //    FilenameFilter filter = new RepositoryFilenameFilter(SUFFIX);
    //    String[] files = directory.list(filter);
    //    String[] result = new String[files.length];
    //
    //    for (int index = 0; index < files.length; index++) {
    //      result[index] = files[index].substring(0,
    //          files[index].indexOf("." + SUFFIX));
    //    }
    //
    //    return result;
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
}