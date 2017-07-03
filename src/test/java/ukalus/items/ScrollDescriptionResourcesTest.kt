package ukalus.items

import junit.framework.TestCase
import java.text.MessageFormat
import java.util.*

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class ScrollDescriptionResourcesTest : TestCase() {
    fun testLoadResource() {
        val bundle = ResourceBundle.getBundle(ScrollDescriptionResource::class.java.name)
        println(bundle.inspect())

        val pattern = bundle.getString(bundle.keys.nextElement())
        println(MessageFormat.format(pattern, *arrayOf<Any>(0)))
        println(MessageFormat.format(pattern, *arrayOf<Any>(1)))
        println(MessageFormat.format(pattern, *arrayOf<Any>(30)))
    }
}