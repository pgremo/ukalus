/*
 * Created on Aug 1, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package next.game;

/**
 * @author a202490
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Turn implements CommandProcessor {

  private static final int TIME_OUT = 5000;
  private Command[] commands;
  private int index;
  private boolean done;

  public Turn(Game game) {
    System.out.println("new turn");
    commands = new Command[game.getPlayers()
      .size()];
    index = 0;
    done = false;
  }

  public void process(Command command) {
    synchronized (commands) {
      commands[index++] = command;

      if (index < commands.length) {
        try {
          if (index == 1) {
            commands.wait(TIME_OUT);
          } else {
            commands.wait();
          }
        } catch (InterruptedException e) {
        }
      }

      boolean processing = false;

      if (!processing) {
        processing = true;

        for (int i = 0; i < commands.length; i++) {
          if (commands[i] != null) {
            commands[i].execute();
          }
        }

        done = true;
        commands.notifyAll();
      }
    }
  }

  public boolean isDone() {
    return done;
  }
}