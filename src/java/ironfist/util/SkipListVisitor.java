/*
 * Created on Feb 11, 2005
 *  
 */
package ironfist.util;

//allow different skip list visitors to implement
//a graphic and a text view
public interface SkipListVisitor {

  public void visit(SkipNode n);
}