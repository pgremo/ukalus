/*
 * Created on Jan 26, 2005
 *
 */
package ironfist.container;

/**
 * @author gremopm
 *  
 */
public interface Transformer<I, O> {

  O transform(I value);

}