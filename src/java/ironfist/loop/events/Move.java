package ironfist.loop.events;

import ironfist.loop.Actor;
import ironfist.loop.Event;
import ironfist.loop.Level;

import java.util.Queue;

public class Move implements Event {

  private Actor source;
  private int start;
  private int buildUp;
  private int coolDown;

  public Move(Actor source, int start, int buildUp, int coolDown) {
    this.source = source;
    this.start = start;
    this.buildUp = buildUp;
    this.coolDown = coolDown;
  }

  public Object getSource() {
    return source;
  }

  public int getTick() {
    return start + buildUp;
  }

  public void process(Level level) {
    // do movement
    Queue<Event> queue = level.getQueue();
    queue.add(new ReadyToAct(source, start + buildUp + coolDown));
  }

}
