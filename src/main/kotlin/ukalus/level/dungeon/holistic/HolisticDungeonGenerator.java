/*
 * Created on May 19, 2003
 *
 */
package ukalus.level.dungeon.holistic;

import ukalus.level.IntLevel;
import ukalus.level.Level;
import ukalus.math.Vector2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author pmgremo
 */
public class HolisticDungeonGenerator {

  private Random randomizer = new Random();
  private double roomProbability = .6;
  private int minRoomHeight = 7;
  private int minRoomWidth = 5;
  private int minRegionHeight = 8;
  private int minRegionWidth = 6;
  private int cuts = 15;

  private Level<Object> level;

  public Level generate() {
    level = new Level<>(new Object[20][80]);

    connectRooms(createRooms(createRegions()));

    return level;
  }

  private List<Region> createRegions() {
    List<Region> result = new ArrayList<Region>();
    List<Region> valid = new LinkedList<Region>();
    valid.add(new Region(0, 0, level.getLength(), level.getWidth()));

    for (int index = 0; index < cuts && valid.size() > 0; index++) {
      Region current = valid.remove(randomizer.nextInt(valid.size()));
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

  private List<Region> createRooms(List<Region> regions) {
    Collections.shuffle(regions, randomizer);
    List<Region> rooms = new LinkedList<>();

    int index = 0;

    for (; index < (regions.size() * roomProbability); index++) {
      Region current = regions.get(index);

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
      Region current = regions.get(index);
      Region room = new Region(current);
      room.setX(current.getX() + randomizer.nextInt(current.getWidth() - 3));
      room.setY(current.getY() + randomizer.nextInt(current.getHeight() - 3));
      room.setWidth(3);
      room.setHeight(3);
      rooms.add(room);
    }

    return rooms;
  }

  private void connectRooms(List<Region> rooms) {
    for (Region cell : rooms) {
      for (int y = 1; y < cell.getHeight() - 1; y++) {
        for (int x = 1; x < cell.getWidth() - 1; x++) {
          level.set(new Vector2D(x + cell.getX(), y + cell.getY()),
            Feature.ROOM);
        }
      }
    }
  }

  public static void main(String[] args) {
    HolisticDungeonGenerator generator = new HolisticDungeonGenerator();

    Level result = generator.generate();
    System.out.println(result);
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

    Vector2D getRandomLocation(Random randomizer) {
      return new Vector2D(x + 1 + randomizer.nextInt(width - 2), y + 1
          + randomizer.nextInt(height - 2));
    }

    public String toString() {
      return "x=" + getX() + ",y=" + getY() + ",width=" + getWidth()
          + ",height=" + getHeight();
    }
  }
}