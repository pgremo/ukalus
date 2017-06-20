/*
 * Created on May 13, 2004
 *  
 */
package ukalus.persistence;

import java.io.IOException;
import java.util.function.Function;

/**
 * Persistence entry point. Commands are executed on a Reference with the
 * capability of making checkpoints.
 * 
 * A checkpoint is a snapshot of the value of the Server's Reference that is
 * stored in the Store.
 * 
 * @author gremopm
 * 
 */
public class Engine {

  private Reference reference = new Reference();
  private Store store;
  private Log log;

  /**
   * Construct a Server with a specific Store and Log.
   * 
   * @param store
   * @param log
   * @throws ClassNotFoundException
   * @throws IOException
   * @throws PersistenceException
   */
  public Engine(Store store, Log log) throws IOException,
      ClassNotFoundException {
    this.store = store;
    this.log = log;

    reference.set(store.load());
    log.forEach(new ReplayLog(reference));
  }

  /**
   * Execute the given command. The command is first added to the Log then it is
   * excuted with the server's Reference.
   * 
   * @param command
   *          to execute.
   * @return the result of the commands execute method.
   * @throws PersistenceException
   */
  public synchronized Object update(Function<Reference,Object> command)
      throws IOException {
    log.add(command);
    return command.apply(reference);
  }

  /**
   * Execute the given command read only. The command is excuted with the
   * server's Reference.
   * 
   * @param command
   *          to execute.
   * @return the result of the commands execute method.
   * @throws PersistenceException
   */
  public synchronized Object query(Function<Reference,Object> command) {
    return command.apply(reference);
  }

  /**
   * Perform a checkpoint on the Server. The current value of the server's
   * Reference is put in the server's Store, then the Log is cleared.
   * 
   * @throws PersistenceException
   */
  public synchronized void checkpoint() throws IOException {
    store.store(reference.get());
    log.clear();
  }

  /**
   * Closes this Engine.
   * 
   * @throws IOException
   */
  public synchronized void close() throws IOException {
    store.close();
    log.close();
  }
}