/*
 * Created on May 13, 2004
 *  
 */
package ukalus.persistence.file

import ukalus.persistence.Reference

import java.io.Serializable
import java.util.function.Function

/**
 * @author gremopm
 */
class MockLogCommand(private val name: String) : Function<Reference<String>, Any?>, Serializable {

    /*
   * (non-Javadoc)
   *
   * @see persistence.Command#execute(java.lang.Object)
   */
    override fun apply(`object`: Reference<String>): Any? = null

    /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
    override fun equals(other: Any?): Boolean = (other as MockLogCommand).name == name

    override fun hashCode(): Int = name.hashCode()

}