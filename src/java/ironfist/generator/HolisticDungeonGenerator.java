/*
 * Created on May 19, 2003
 *
 */
package ironfist.generator;

import ironfist.astar.AStar;
import ironfist.loop.Level;
import ironfist.math.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author pmgremo
 */
public class HolisticDungeonGenerator {

  private Random randomizer = new Random();
  private double roomProbability = 1.0;
  private int minRoomHeight = 5;
  private int minRoomWidth = 4;
  private int minRegionHeight = 8;
  private int minRegionWidth = 5;
  private int cuts = 15;

  private Level level;

  public Level generate() {
    level = new Level(new Object[20][80]);

    connectRooms(createRooms(createRegions()));

    return level;
  }

  private List createRegions() {
    List result = new ArrayList();
    List valid = new LinkedList();
    valid.add(new Region(0, 0, level.getLength(), level.getWidth()));

    for (int index = 0; index < cuts && valid.size() > 0; index++) {
      Region current = (Region) valid.remove(randomizer.nextInt(valid.size()));
      Region next = null;

      boolean splitWidth = current.getWidth() > minRegionWidth * 2;
      boolean splitHeight = current.getHeight() > minRegionHeight * 2;

      if (splitWidth && splitHeight) {
        if (randomizer.nextBoolean()) {
          splitWidth = false;
        } else {
          splitHeight = false;
        }
      }

      if (splitHeight) {
        int height = minRegionHeight
            + randomizer.nextInt(current.getHeight() - (minRegionHeight * 2));
        current.setHeight(current.getHeight() - height);
        next = new Region(current.getX(), current.getY() + current.getHeight()
            + 1, current.getWidth(), height - 1);
      } else if (splitWidth) {
        int width = minRegionWidth
            + randomizer.nextInt(current.getWidth() - (minRegionWidth * 2));
        current.setWidth(current.getWidth() - width);
        next = new Region(current.getX() + current.getWidth() + 1,
          current.getY(), width - 1, current.getHeight());
      }

      if ((current.getWidth() > (minRegionWidth * 2))
          || (current.getHeight() > (minRegionHeight * 2))) {
        valid.add(current);
      } else {
        result.add(current);
      }

      if (next != null) {
        if (next.getWidth() > minRegionWidth * 2
            || next.getHeight() > minRegionHeight * 2) {
          valid.add(next);
        } else {
          result.add(next);
        }
      }
    }

    result.addAll(valid);

    return result;
  }

  private List createRooms(List regions) {
    Collections.shuffle(regions, randomizer);
    List rooms = new LinkedList();

    int index = 0;

    for (; index < (regions.size() * roomProbability); index++) {
      Region current = (Region) regions.get(index);

      Region room = new Region(current);

      if (current.getWidth() > minRoomWidth) {
        room.setWidth(minRoomWidth
            + randomizer.nextInt(current.getWidth() - minRoomWidth));

        if (current.getWidth() > room.getWidth()) {
          room.setX(current.getX()
              + randomizer.nextInt(current.getWidth() - room.getWidth()));
        }
      }

      if (current.getHeight() > minRoomHeight) {
        room.setHeight(minRoomHeight
            + randomizer.nextInt(current.getHeight() - minRoomHeight));

        if (current.getHeight() > room.getHeight()) {
          room.setY(current.getY()
              + randomizer.nextInt(current.getHeight() - room.getHeight()));
        }
      }

      rooms.add(room);
    }

    for (; index < regions.size(); index++) {
      Region current = (Region) regions.get(index);
      Region room = new Region(current);
      room.setX(current.getX() + randomizer.nextInt(current.getWidth() - 3));
      room.setY(current.getY() + randomizer.nextInt(current.getHeight() - 3));
      room.setWidth(3);
      room.setHeight(3);
      rooms.add(room);
    }

    return rooms;
  }

  private void connectRooms(List rooms) {
    Iterator iterator = rooms.iterator();
    while (iterator.hasNext()) {
      Region cell = (Region) iterator.next();

      for (int y = 1; y < cell.getHeight() - 1; y++) {
        for (int x = 1; x < cell.getWidth() - 1; x++) {
          level.set(new Vector(x + cell.getX(), y + cell.getY()), Feature.ROOM);
        }
      }
    }

    AStar finder = new AStar();
    Collections.shuffle(rooms, randomizer);
    iterator = rooms.iterator();
    if (iterator.hasNext()) {
      Region startRoom = (Region) iterator.next();
      if (iterator.hasNext()) {
        while (iterator.hasNext()) {
          Region stopRoom = (Region) iterator.next();
          PassageNode start = new PassageNode(level,
            startRoom.getRandomLocation(randomizer), null);
          PassageNode stop = new PassageNode(level,
            stopRoom.getRandomLocation(randomizer), null);
          Iterator path = finder.solve(new PassageHeuristic(start, stop),
            new FixedCost(1), start, stop);
          while (path.hasNext()) {
            Vector location = ((PassageNode) path.next()).getLocation();
            if (level.get(location) == null)
              level.set(location, Feature.PASSAGE);
          }
          startRoom = stopRoom;
        }
      }
    }
  }

  public static void main(String[] args) {
    HolisticDungeonGenerator generator = new HolisticDungeonGenerator();

    Level result = generator.generate();

    for (int x = 0; x < result.getLength(); x++) {
      for (int y = 0; y < result.getWidth(); y++) {
        if (result.get(new Vector(x, y)) != null) {
          System.out.print(".");
        } else {
          System.out.print("#");
        }
      }

      System.out.println();
    }
  }

  private class Region {

    private int x;
    private int y;
    private int width;
    private int height;

    public Region(Region region) {
      setX(region.getX());
      setY(region.getY());
      setWidth(region.getWidth());
      setHeight(region.getHeight());
    }

    public Region(int x, int y, int width, int height) {
      setX(x);
      setY(y);
      setWidth(width);
      setHeight(height);
    }

    public void setHeight(int height) {
      this.height = height;
    }

    public int getHeight() {
      return height;
    }

    void setWidth(int width) {
      this.width = width;
    }

    int getWidth() {
      return width;
    }

    void setX(int x) {
      this.x = x;
    }

    int getX() {
      return x;
    }

    void setY(int y) {
      this.y = y;
    }

    int getY() {
      return y;
    }

    Vector getRandomLocation(Random randomizer) {
      return new Vector(x + 1 + randomizer.nextInt(width - 2), y + 1
          + randomizer.nextInt(height - 2));
    }

    public String toString() {
      return "x=" + getX() + ",y=" + getY() + ",width=" + getWidth()
          + ",height=" + getHeight();
    }
  }
}