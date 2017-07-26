package ukalus.items

import junit.framework.TestCase
import java.text.MessageFormat
import java.util.*

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class RingIdentityResourcesTest : TestCase() {

    fun testLoadResource() {
        val bundle = ResourceBundle.getBundle("ukalus.items.RingIdentityResources")
        val pattern = bundle.getString("ring.protection.identity")
        val formatter = MessageFormat(pattern)
        println(formatter.format(arrayOf(-1)))
        println(formatter.format(arrayOf(1, -1)))
        println(formatter.format(arrayOf(1, 12)))
        println(formatter.format(arrayOf(2, 0)))
        println(formatter.format(arrayOf(3, 2)))

    }
}