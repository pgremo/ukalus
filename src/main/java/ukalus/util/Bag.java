/*
 * Created on Aug 19, 2004
 *
 */
package ukalus.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gremopm
 *  
 */
public interface Bag<E> extends Collection<E> {

	int occurrence(E value);
	
	Iterator<Map.Entry<E, AtomicInteger>> occurrenceIterator();
}