/*
 * Created on Aug 19, 2004
 *
 */
package ironfist.util;

import java.util.Collection;

/**
 * @author gremopm
 *  
 */
public interface Bag extends Collection {

	int occurence(Object value);
}