package ironfist.loop.events;

import ironfist.loop.Actor;
import ironfist.loop.Event;
import ironfist.loop.Level;

public class ReadyToAct implements Event {

  private Actor source;
  private int turn;

  public ReadyToAct(Actor source, int turn) {
    this.source = source;
    this.turn = turn;
  }

  public Object getSource() {
    return source;
  }

  public int getTurn() {
    return turn;
  }

  public void perform(Level level) {
    source.act(level);
  }

}
