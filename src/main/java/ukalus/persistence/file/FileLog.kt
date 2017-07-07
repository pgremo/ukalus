/*
 * Created on May 13, 2004
 *  
 */
package ukalus.persistence.file

import ukalus.persistence.Log
import ukalus.persistence.SerializedObjectIterator

import java.io.*

/**
 * @author gremopm
 */
class FileLog<T>(file: File) : Log<T> {

    private val channel = RandomAccessFile(file, "rw")

    override fun clear() {
        channel.setLength(0)
    }

    override fun add(o: T) {
        val buffer = ByteArrayOutputStream()
        val stream = ObjectOutputStream(buffer)
        stream.writeObject(o)
        val data = buffer.toByteArray()
        channel.seek(channel.length())
        channel.writeInt(data.size)
        channel.write(data)
    }

    override fun iterator(): Iterator<T> {
        try {
            channel.seek(0)
            return SerializedObjectIterator(channel)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @Throws(IOException::class)
    override fun close() {
        channel.close()
    }
}