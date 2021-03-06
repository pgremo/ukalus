/*
 * Created on Mar 18, 2005
 *
 */
package ukalus.persistence

import java.util.function.Consumer
import java.util.function.Function

class ReplayLog<T>(private val reference: Reference<T?>) : Consumer<Function<Reference<T?>, *>> {
    override fun accept(function: Function<Reference<T?>, *>) {
        try {
            function.apply(reference)
        } catch (e: Exception) {
        }
    }
}
