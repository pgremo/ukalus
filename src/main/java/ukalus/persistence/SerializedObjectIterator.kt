/*
 * Created on Sep 27, 2004
 *
 */
package ukalus.persistence

import java.io.ByteArrayInputStream
import java.io.ObjectInputStream
import java.io.RandomAccessFile

/**
 * @author gremopm
 */
class SerializedObjectIterator<out E>(private val channel: RandomAccessFile) : Iterator<E> {
    private var data = ByteArray(0)

    override fun hasNext() = channel.filePointer < channel.length()

    override fun next(): E {
        val size = channel.readInt()
        if (size > data.size) {
            data = ByteArray(size)
        }
        if (channel.read(data, 0, size) == size) {
            return ObjectInputStream(ByteArrayInputStream(data, 0, size)).use { it.readObject() as E }
        } else {
            throw RuntimeException("source corrupted")
        }
    }
}