/*
 * Created on Jul 31, 2003
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
public class Server {
  private SessionManager sessionManager = new SessionManager();
  private ApplicationManager applicationManager = new ApplicationManager();

  public Response execute(Request request) {
    Response response = new Response();

    try {
      Session session = sessionManager.get(request, response);
      Application application = applicationManager.get(request, response);
      session.setApplication(application);
      application.service(request, response, session);
    } catch (Exception e) {
      response.setError(e);
    }

    return response;
  }
}
