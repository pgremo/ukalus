/*
 * Created on Aug 1, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package next.server;

/**
 * @author a202490
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface Application {

  void service(Request request, Response response, Session session);
}