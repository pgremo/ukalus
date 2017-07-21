package ukalus.items

import junit.framework.TestCase
import java.text.MessageFormat.format
import java.util.*

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class RingDescriptionResourcesTest : TestCase() {

    fun testLoadResource() {
        val bundle = ResourceBundle.getBundle("ukalus.items.RingDescriptionResources")
        val pattern = bundle.getString(bundle.keys.nextElement())
        println(format(pattern, 0))
        println(format(pattern, 1))
        println(format(pattern, 30))
    }
}