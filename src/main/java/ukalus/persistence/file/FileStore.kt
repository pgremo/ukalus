/*
 * Created on May 13, 2004
 *  
 */
package ukalus.persistence.file

import ukalus.persistence.Store
import java.io.*

/**
 * @author gremopm
 */
class FileStore<T : Serializable>(private val file: File) : Store<T> {

    @Throws(IOException::class)
    override fun store(`object`: T?) {
        val temp = File("${file.absolutePath}-${System.currentTimeMillis()}")
        ObjectOutputStream(FileOutputStream(temp)).use {
            it.writeObject(`object`)
            it.flush()
            it.close()
        }
        file.delete()
        if (!temp.renameTo(file)) {
            throw IOException("unable to rename $temp to $file")
        }
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    override fun load(): T? = if (file.exists()) {
        ObjectInputStream(FileInputStream(file)).use {
            it.readObject() as T
        }
    } else {
        null
    }

    override fun close() {}
}