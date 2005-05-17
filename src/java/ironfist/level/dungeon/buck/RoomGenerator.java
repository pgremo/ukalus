package ironfist.level.dungeon.buck;

import ironfist.level.Region;
import ironfist.level.RegionFactory;

import java.util.Random;

public class RoomGenerator implements RegionFactory {

  private Random random;
  private int minRoomHeight = 5;
  private int maxRoomHeight = 9;
  private int minRoomWidth = 9;
  private int maxRoomWidth = 14;
  private int startId;

  public RoomGenerator(Random random, int minRoomHeight, int maxRoomHeight,
      int minRoomWidth, int maxRoomWidth, int startId) {
    this.random = random;
    this.minRoomHeight = minRoomHeight;
    this.maxRoomHeight = maxRoomHeight;
    this.minRoomWidth = minRoomWidth;
    this.maxRoomWidth = maxRoomWidth;
    this.startId = startId;
  }

  public Region create() {
    return new Room(random, random.nextInt(maxRoomHeight - minRoomHeight)
        + minRoomHeight, random.nextInt(maxRoomWidth - minRoomWidth)
        + minRoomWidth, startId++);
  }

}
