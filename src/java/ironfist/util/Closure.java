/*
 * Created on Jan 26, 2005
 *
 */
package ironfist.util;

import java.io.Serializable;

/**
 * @author gremopm
 * 
 */
public interface Closure<I, O> extends Serializable {

  O apply(I value);

}