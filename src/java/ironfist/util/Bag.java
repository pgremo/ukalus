/*
 * Created on Aug 19, 2004
 *
 */
package ironfist.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @author gremopm
 *  
 */
public interface Bag<E> extends Collection<E> {

	int occurence(Object value);
	
	Iterator<Map.Entry<E, Counter>> occurenceIterator();
}