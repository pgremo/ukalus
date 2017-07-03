/*
 * Created on Feb 25, 2005
 *
 */
package ukalus.items

import junit.framework.TestCase
import java.util.*

/**
 * @author gremopm
 */
class ArtDescriptionResourceTest : TestCase() {

    fun testGenerate() {
        println(ResourceBundle.getBundle(ArtDescriptionResource::class.java.name).inspect())
    }

}

fun ResourceBundle.inspect(): String {
    return this.keys
            .asSequence()
            .joinToString(separator = "\n", transform = { it -> "$it=${this.getString(it)}" })
}