package ironfist.generator;

import ironfist.Floor;
import ironfist.Level;
import ironfist.Stairs;
import ironfist.Tile;
import ironfist.TileType;
import ironfist.Wall;
import ironfist.Weapon;
import ironfist.geometry.Vector;

import java.util.List;
import java.util.Random;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class HomeGenerator {

  private static final TileTypePredicate tilePredicate = new TileTypePredicate();
  private static final EmptyFloorTilePredicate stairPredicate = new EmptyFloorTilePredicate();
  private static final DungeonRoomComparator roomComparator = new DungeonRoomComparator();
  private RectangleRoomFactory areaFactory = new RectangleRoomFactory();

  {
    areaFactory.setMinRoomHeight(7);
    areaFactory.setMinRoomWidth(7);
    areaFactory.setMaxRoomHeight(7);
    areaFactory.setMaxRoomWidth(7);
    areaFactory.setFloorClass(Floor.class);
    areaFactory.setWallClass(Wall.class);
    areaFactory.setCornerClass(Barrier.class);
    areaFactory.setRandomizer(new Random());
  }

  /**
   * DOCUMENT ME!
   * 
   * @param name
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Level generate(String name) {
    Level result = new Level(name);
    List list = areaFactory.create();
    Area room = new Area(list);
    stairPredicate.setTileTypeClass(Floor.class);

    Tile downLocation = room.getRandom(stairPredicate);
    ((Floor) downLocation.getTileType()).setPortal(new Stairs(Stairs.DOWN));
    Tile itemLocation = room.getRandom(stairPredicate);
    ((Floor) itemLocation.getTileType()).addThing(new Weapon());
    itemLocation = room.getRandom(stairPredicate);
    ((Floor) itemLocation.getTileType()).addThing(new Weapon());

    Vector coordinate = new Vector((result.getHeight() - 7) / 2,
      (result.getWidth() - 7) / 2);

    room.setCoordinate(coordinate);
    room.place(result);

    return result;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param args
   *          DOCUMENT ME!
   * 
   * @throws Exception
   *           DOCUMENT ME!
   */
  public static void main(String[] args) throws Exception {
    HomeGenerator generator = new HomeGenerator();
    Level dungeon = generator.generate("0");

    StringBuffer output = new StringBuffer();

    for (int x = 0; x < dungeon.getHeight(); x++) {
      for (int y = 0; y < dungeon.getWidth(); y++) {
        Tile current = dungeon.get(new Vector(x, y));
        TileType type = null;

        if (current != null) {
          type = current.getTileType();
        }

        if (type == null) {
          output.append(" ");
        } else if (type instanceof Wall) {
          output.append("#");
        } else {
          Floor floor = (Floor) type;

          if (floor.getDoor() != null) {
            output.append("+");
          } else if (floor.getPortal() != null) {
            if (((Stairs) floor.getPortal()).getDirection()
              .equals(Stairs.DOWN)) {
              output.append(">");
            } else if (((Stairs) floor.getPortal()).getDirection()
              .equals(Stairs.UP)) {
              output.append("<");
            }
          } else {
            output.append(".");
          }
        }
      }

      output.append("\n");
    }

    System.out.println(output.toString());
  }
}