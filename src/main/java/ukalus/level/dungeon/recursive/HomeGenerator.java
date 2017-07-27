package ukalus.level.dungeon.recursive;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomAdaptor;
import ukalus.Floor;
import ukalus.Level;
import ukalus.Stairs;
import ukalus.Tile;
import ukalus.TileType;
import ukalus.Wall;
import ukalus.Weapon;
import ukalus.math.Vector2D;

import java.util.List;
import java.util.Random;

public class HomeGenerator {

  private Random random = new RandomAdaptor(new MersenneTwister());
  private RectangleRoomFactory areaFactory;

  {
    areaFactory = new RectangleRoomFactory(random, Floor.class, Wall.class,
      Terminal.class, 7, 7, 7, 7);
  }

  public Level generate(String name) {
    Level result = new Level(name);
    List<Tile> list = areaFactory.create();
    Area room = new Area(list);
    EmptyFloorTilePredicate stairPredicate = new EmptyFloorTilePredicate();

    Tile downLocation = room.getRandom(stairPredicate, random);
    ((Floor) downLocation.getTileType()).setPortal(new Stairs(Stairs.Companion.getDOWN()));
    Tile itemLocation = room.getRandom(stairPredicate, random);
    ((Floor) itemLocation.getTileType()).addThing(new Weapon());
    itemLocation = room.getRandom(stairPredicate, random);
    ((Floor) itemLocation.getTileType()).addThing(new Weapon());

    Vector2D coordinate = new Vector2D((result.getHeight() - 7) / 2,
      (result.getWidth() - 7) / 2);

    room.setCoordinate(coordinate);
    room.place(result);

    return result;
  }

  public static void main(String[] args) throws Exception {
    HomeGenerator generator = new HomeGenerator();
    Level dungeon = generator.generate("0");

    StringBuffer output = new StringBuffer();

    for (int x = 0; x < dungeon.getHeight(); x++) {
      for (int y = 0; y < dungeon.getWidth(); y++) {
        Tile current = dungeon.get(new Vector2D(x, y));
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
              .equals(Stairs.Companion.getDOWN())) {
              output.append(">");
            } else if (((Stairs) floor.getPortal()).getDirection()
              .equals(Stairs.Companion.getUP())) {
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