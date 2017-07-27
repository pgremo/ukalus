/*
 * Created on Mar 20, 2005
 *
 */
package ukalus.container

interface Resolver {
    fun getValue(type: Class<*>): Any?
}
