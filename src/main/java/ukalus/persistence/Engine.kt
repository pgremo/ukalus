/*
 * Created on May 13, 2004
 *  
 */
package ukalus.persistence

import java.io.IOException
import java.io.Serializable
import java.util.function.Function

/**
 * Persistence entry point. Commands are executed on a Reference with the
 * capability of making checkpoints.
 *
 *
 * A checkpoint is a snapshot of the value of the Server's Reference that is
 * stored in the Store.

 * @author gremopm
 */
class Engine<T : Serializable>
/**
 * Construct a Server with a specific Store and Log.

 * @param store
 * *
 * @param log
 * *
 * @throws ClassNotFoundException
 * *
 * @throws IOException
 */
@Throws(IOException::class, ClassNotFoundException::class)
constructor(private val store: Store<T>, private val log: Log<Function<Reference<T?>, Any?>>) {

    private val reference = Reference<T?>()

    init {
        reference.set(store.load())
        log.forEach(ReplayLog(reference))
    }

    /**
     * Execute the given command. The command is first added to the Log then it is
     * excuted with the server's Reference.

     * @param command to execute.
     * *
     * @return the result of the commands execute method.
     */
    @Synchronized @Throws(IOException::class)
    fun update(command: Function<Reference<T?>, Any?>): Any? {
        log.add(command)
        return command.apply(reference)
    }

    /**
     * Execute the given command read only. The command is executed with the
     * server's Reference.

     * @param command to execute.
     * *
     * @return the result of the commands execute method.
     */
    @Synchronized fun query(command: Function<Reference<T?>, Any?>): Any? {
        return command.apply(reference)
    }

    /**
     * Perform a checkpoint on the Server. The current value of the server's
     * Reference is put in the server's Store, then the Log is cleared.

     * @throws PersistenceException
     */
    @Synchronized @Throws(IOException::class)
    fun checkpoint() {
        store.store(reference.get())
        log.clear()
    }

    /**
     * Closes this Engine.

     * @throws IOException
     */
    @Synchronized @Throws(IOException::class)
    fun close() {
        store.close()
        log.close()
    }
}