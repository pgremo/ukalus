/*
 * Created on Jul 31, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package next.game;

import next.game.commands.JoinGameCommand;
import next.game.commands.QuitCommand;
import next.game.commands.WaitCommand;

import next.server.Request;
import next.server.Response;
import next.server.Session;

import java.util.HashMap;
import java.util.Map;


/**
 * @author a202490
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CommandManager {
  private static final String COMMAND_NAME = "commandName";
  private Map commands = new HashMap();

  {
    Command command = new QuitCommand();
    command.setName("quit");
    commands.put(command.getName(), command);
    command = new JoinGameCommand();
    command.setName("join");
    commands.put(command.getName(), command);
    command = new WaitCommand();
    command.setName("wait");
    commands.put(command.getName(), command);
  }

  public Command get(Request request, Response response, Session session) {
    String commandName = (String) request.getParameter(COMMAND_NAME);
    Command command = (Command) commands.get(commandName);
    command.setRequest(request);
    command.setResponse(response);
    command.setSession(session);

    return command;
  }
}
