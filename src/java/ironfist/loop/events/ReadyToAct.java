package ironfist.loop.events;

import ironfist.loop.Actor;
import ironfist.loop.Event;
import ironfist.loop.Level;

public class ReadyToAct implements Event {

  private Actor source;
  private int tick;

  public ReadyToAct(Actor source, int tick) {
    this.source = source;
    this.tick = tick;
  }

  public Object getSource() {
    return source;
  }

  public int getTick() {
    return tick;
  }

  public void process(Level level) {
    source.act(level);
  }

}
