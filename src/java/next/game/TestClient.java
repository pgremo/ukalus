/*
 * Created on Jul 31, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package next.game;

import java.io.InputStreamReader;
import java.io.StreamTokenizer;

import next.server.Request;
import next.server.Response;
import next.server.Server;

/**
 * @author a202490
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TestClient {

  private static final String EXIT_PARAMETER = "exit";

  public static final void main(String[] args) throws Exception {
    Server server = new Server();
    StreamTokenizer tokenizer = new StreamTokenizer(new InputStreamReader(
      System.in));
    tokenizer.eolIsSignificant(true);

    boolean quit = false;

    while (!quit) {
      String command = null;
      tokenizer.nextToken();

      while (tokenizer.ttype != StreamTokenizer.TT_EOL) {
        if (command == null) {
          command = tokenizer.sval;
        }

        tokenizer.nextToken();
      }

      if (command != null) {
        Request request = new Request();
        request.setApplicationName("test");
        request.setParameter("commandName", command);

        Response response = server.execute(request);

        if (Boolean.TRUE.equals(response.getParameter(EXIT_PARAMETER))) {
          quit = true;
        }
      }
    }
  }
}