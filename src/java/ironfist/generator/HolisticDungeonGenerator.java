/*
 * Created on May 19, 2003
 *
 */
package ironfist.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


/**
 * DOCUMENT ME!
 *
 * @author pmgremo
 */
public class HolisticDungeonGenerator {
  private Random randomizer = new Random();
  private double room_probability = .8;
  private int min_room_height = 4;
  private int min_room_width = 5;
  private int min_region_height = 5;
  private int min_region_width = 8;
  private int cuts = 15;
  private int[][] level;

  public int[][] generate() {
    level = new int[20][80];

    List cells = createRegions();
    createRooms(cells);

    connectRooms(cells);

    //    drawDungeon(cells);
    return level;
  }

  private List createRegions() {
    List result = new ArrayList();
    List valid = new LinkedList();
    valid.add(new Region(0, 0, level[0].length, level.length));

    boolean direction = false;

    for (int index = 0; (index < cuts) && (valid.size() > 0); index++) {
      Region current = (Region) valid.remove((valid.size() > 1)
          ? randomizer.nextInt(valid.size() - 1) : 0);
      Region next = null;
      boolean splitWidth = current.getWidth() > (min_region_width * 2);
      boolean splitHeight = current.getHeight() > (min_region_height * 2);

      if ((splitHeight && !splitWidth) ||
          (splitWidth && splitHeight && direction)) {
        int height = min_region_height +
          randomizer.nextInt(current.getHeight() - (min_region_height * 2));
        current.setHeight(current.getHeight() - height);
        next = new Region(current.getX(),
            current.getY() + current.getHeight() + 1, current.getWidth(),
            height - 1);
      } else if (splitWidth) {
        int width = min_region_width +
          randomizer.nextInt(current.getWidth() - (min_region_width * 2));
        current.setWidth(current.getWidth() - width);
        next = new Region(current.getX() + current.getWidth() + 1,
            current.getY(), width - 1, current.getHeight());
      }

      direction = !direction;

      if ((current.getWidth() > (min_region_width * 2)) ||
          (current.getHeight() > (min_region_height * 2))) {
        valid.add(current);
      } else {
        result.add(current);
      }

      if (next != null) {
        if ((next.getWidth() > (min_region_width * 2)) ||
            (next.getHeight() > (min_region_height * 2))) {
          valid.add(next);
        } else {
          result.add(next);
        }
      }
    }

    result.addAll(valid);

    return result;
  }

  //  private void drawDungeon(List cells) {
  //    Iterator iterator = cells.iterator();
  //
  //    while (iterator.hasNext()) {
  //      Region cell = (Region) iterator.next();
  //
  //      if (cell.room != null) {
  //        cell = cell.room;
  //
  //        for (int x = cell.getX(); x <= (cell.getWidth() + cell.getX()); x++) {
  //          level[cell.getY()][x] = true;
  //          level[cell.getY() + cell.getHeight()][x] = true;
  //        }
  //
  //        for (int y = cell.getY(); y <= (cell.getHeight() + cell.getY()); y++) {
  //          level[y][cell.getX()] = true;
  //          level[y][cell.getX() + cell.getWidth()] = true;
  //        }
  //      }
  //    }
  //  }
  //
  //  private void drawDungeon(int[][] data) {
  //    for (int y = 0; y < data.length; y++) {
  //      for (int x = 0; x < data[y].length; x++) {
  //        if ((data[y][x] == 2) || (data[y][x] == 4)) {
  //          level[y][x] = true;
  //        }
  //      }
  //    }
  //  }
  private void createRooms(List regions) {
    Collections.shuffle(regions, randomizer);

    int index = 0;

    for (; index < (((double) regions.size()) * room_probability); index++) {
      Region current = (Region) regions.get(index);

      Region room = new Region(current);

      if (current.getWidth() > min_room_width) {
        room.setWidth(min_room_width +
          randomizer.nextInt(current.getWidth() - min_room_width));

        if (current.getWidth() > room.getWidth()) {
          room.setX(current.getX() +
            randomizer.nextInt(current.getWidth() - room.getWidth()));
        }
      }

      if (current.getHeight() > min_room_height) {
        room.setHeight(min_room_height +
          randomizer.nextInt(current.getHeight() - min_room_height));

        if (current.getHeight() > room.getHeight()) {
          room.setY(current.getY() +
            randomizer.nextInt(current.getHeight() - room.getHeight()));
        }
      }

      current.room = room;
    }

    for (; index < regions.size(); index++) {
      Region current = (Region) regions.get(index);
      Region room = new Region(current);
      room.setX(current.getX() + randomizer.nextInt(current.getWidth() - 3));
      room.setY(current.getY() + randomizer.nextInt(current.getHeight() - 3));
      room.setWidth(3);
      room.setHeight(3);
      current.room = room;
    }
  }

  private void connectRooms(List regions) {
    int floor = 1;
    int wall = 2;
    int corner = 4;
    int connected = 8;
    Iterator iterator = regions.iterator();

    while (iterator.hasNext()) {
      Region cell = (Region) iterator.next();

      if (cell.room != null) {
        cell = cell.room;

        for (int y = 0; y < cell.getHeight(); y++) {
          for (int x = 0; x < cell.getWidth(); x++) {
            if (((y == 0) && (x == 0)) ||
                ((y == (cell.getHeight() - 1)) && (x == 0)) ||
                ((y == 0) && (x == (cell.getWidth() - 1))) ||
                ((y == (cell.getHeight() - 1)) && (x == (cell.getWidth() - 1)))) {
              level[y + cell.getY()][x + cell.getX()] = corner;
            } else if ((y == 0) || (x == 0) || (y == (cell.getHeight() - 1)) ||
                (x == (cell.getWidth() - 1))) {
              level[y + cell.getY()][x + cell.getX()] = wall;
            } else {
              level[y + cell.getY()][x + cell.getX()] = floor;
            }
          }
        }
      }
    }

    Collections.shuffle(regions, randomizer);

    Region current = (Region) regions.get(1);

    for (int index = 0; index < regions.size(); index++) {
      Region next = (Region) regions.get(index);

      if (next.room != null) {
      }
    }

    //    drawDungeon(map);
  }

  public static void main(String[] args) {
    HolisticDungeonGenerator generator = new HolisticDungeonGenerator();

    //generator.setRandom(new MersenneTwister());
    int[][] result = generator.generate();

    for (int x = 0; x < result.length; x++) {
      for (int y = 0; y < result[x].length; y++) {
        if (result[x][y] == 1) {
          System.out.print(".");
        } else if (result[x][y] == 2) {
          System.out.print("#");
        } else if (result[x][y] == 4) {
          System.out.print("*");
        } else {
          System.out.print(" ");
        }
      }

      System.out.println();
    }
  }

  private class Region {
    Region room;
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
      return "x=" + getX() + ",y=" + getY() + ",width=" + getWidth() +
      ",height=" + getHeight();
    }
  }
}
