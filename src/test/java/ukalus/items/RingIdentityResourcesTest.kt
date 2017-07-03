package ukalus.items

import java.text.MessageFormat
import java.util.ResourceBundle

import junit.framework.TestCase

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class RingIdentityResourcesTest : TestCase() {

    fun testLoadResource() {
        val bundle = ResourceBundle.getBundle("ukalus.items.RingIdentityResources")
        val pattern = bundle.getString("ring.protection.identity")
        println(MessageFormat.format(pattern, *arrayOf<Any>(-1)))
        println(MessageFormat.format(pattern, *arrayOf<Any>(1, -1)))
        println(MessageFormat.format(pattern, *arrayOf<Any>(1, +12)))
        println(MessageFormat.format(pattern, *arrayOf<Any>(2, 0)))
        println(MessageFormat.format(pattern, *arrayOf<Any>(3, +2)))
    }
}