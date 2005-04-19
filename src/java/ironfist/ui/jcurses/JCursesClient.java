package ironfist.ui.jcurses;

import ironfist.Client;
import ironfist.Command;
import ironfist.CommandType;
import ironfist.Creature;
import ironfist.Door;
import ironfist.Floor;
import ironfist.Level;
import ironfist.Stairs;
import ironfist.Thing;
import ironfist.Tile;
import ironfist.TileType;
import ironfist.Wall;
import ironfist.Weapon;
import ironfist.math.Vector2D;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jcurses.event.ActionEvent;
import jcurses.event.ActionListener;
import jcurses.event.ActionListenerManager;
import jcurses.system.CharColor;
import jcurses.system.InputChar;
import jcurses.system.Toolkit;
import jcurses.util.Rectangle;
import jcurses.widgets.Widget;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class JCursesClient extends Widget implements Client {

  private static Map<Integer, Vector2D> directions;
  private static Map<Integer, CommandType> commandTypes;
  private static Map<Marker, String> symbols;

  static {
    directions = new HashMap<Integer, Vector2D>();
    directions.put(new Integer(InputChar.KEY_UP), Vector2D.get(-1, 0));
    directions.put(new Integer(InputChar.KEY_RIGHT), Vector2D.get(0, 1));
    directions.put(new Integer(InputChar.KEY_DOWN), Vector2D.get(1, 0));
    directions.put(new Integer(InputChar.KEY_LEFT), Vector2D.get(0, -1));
    directions.put(new Integer('8'), Vector2D.get(-1, 0));
    directions.put(new Integer('9'), Vector2D.get(-1, 1));
    directions.put(new Integer('6'), Vector2D.get(0, 1));
    directions.put(new Integer('3'), Vector2D.get(1, 1));
    directions.put(new Integer('2'), Vector2D.get(1, 0));
    directions.put(new Integer('1'), Vector2D.get(1, -1));
    directions.put(new Integer('4'), Vector2D.get(0, -1));
    directions.put(new Integer('7'), Vector2D.get(-1, -1));

    commandTypes = new HashMap<Integer, CommandType>();
    commandTypes.put(new Integer('c'), CommandType.CLOSE);
    commandTypes.put(new Integer('o'), CommandType.OPEN);
    commandTypes.put(new Integer('<'), CommandType.UP);
    commandTypes.put(new Integer('>'), CommandType.DOWN);
    commandTypes.put(new Integer('Q'), CommandType.QUIT);
    commandTypes.put(new Integer('S'), CommandType.SAVE);
    commandTypes.put(new Integer(','), CommandType.PICKUP);
    commandTypes.put(new Integer('d'), CommandType.DROP);
    commandTypes.put(new Integer('.'), CommandType.WAIT);
    commandTypes.put(new Integer('i'), CommandType.INVENTORY);

    symbols = new HashMap<Marker, String>();
    symbols.put(null, " ");
    symbols.put(Marker.WALL, "#");
    symbols.put(Marker.DOOR_CLOSED, "+");
    symbols.put(Marker.DOOR_OPEN, "/");
    symbols.put(Marker.FLOOR, ".");
    symbols.put(Marker.STAIRS_DOWN, ">");
    symbols.put(Marker.STAIRS_UP, "<");
    symbols.put(Marker.WEAPON, "(");
    symbols.put(Marker.HERO, "@");
  }

  private transient ActionListenerManager manager = new ActionListenerManager();
  private transient List<Tile> vision;
  private transient Plan plan;
  private transient CommandType commandType;
  private transient Command command;
  private transient boolean commandReady;
  private transient Creature hero;
  private transient PlanBook planBook;

  /**
   * Creates a new JCursesClient object.
   * 
   * @param creature
   *          DOCUMENT ME!
   */
  public JCursesClient(Creature creature) {
    hero = creature;
    planBook = (PlanBook) hero.getProperty("planBook");

    if (planBook == null) {
      planBook = new PlanBook();
      hero.setProperty("planBook", planBook);
    }

    onLevelChange(hero.getLevel());
  }

  /**
   * DOCUMENT ME!
   * 
   * @param level
   *          DOCUMENT ME!
   */
  public void onLevelChange(Level level) {
    String name = level.getName() + "-plan";
    planBook.turnTo(name);
    plan = (Plan) planBook.get();

    if (plan == null) {
      plan = new Plan(name, level.getHeight(), level.getWidth());
      planBook.add(name, plan);
    }

    vision = null;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param list
   *          DOCUMENT ME!
   */
  public void onVisionChange(List<Tile> list) {
    if (vision != null) {
      vision.removeAll(list);
    }

    updateFromPlan(vision);

    vision = list;
    updatePlan(vision);
    updateFromVision(vision);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param list
   *          DOCUMENT ME!
   */
  private void updatePlan(List<Tile> list) {
    Iterator<Tile> iterator = list.iterator();

    while (iterator.hasNext()) {
      Tile current = iterator.next();
      plan.set(current.getCoordinate(), current.getTileType());
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param list
   *          DOCUMENT ME!
   */
  private void updateFromPlan(List<Tile> list) {
    CharColor colors = new CharColor(CharColor.BLACK, CharColor.WHITE,
      CharColor.NORMAL);

    if (list == null) {
      StringBuffer buffer = new StringBuffer();

      for (int x = 0; x < plan.getHeight(); x++) {
        for (int y = 0; y < plan.getWidth(); y++) {
          buffer.append(symbols.get(determineLevelMarker(plan.get(Vector2D.get(x,
            y)))));
        }

        buffer.append("\n");
      }

      Rectangle area = new Rectangle(getAbsoluteX(), getAbsoluteY(),
        plan.getWidth(), plan.getHeight());
      Toolkit.printString(buffer.toString(), area, colors);
    } else {
      Iterator<Tile> iterator = list.iterator();

      while (iterator.hasNext()) {
        Tile tile = iterator.next();
        Vector2D coordinate = tile.getCoordinate();
        Toolkit.printString(
          symbols.get(determineLevelMarker(plan.get(coordinate))),
          getAbsoluteX() + (int) coordinate.getY(), getAbsoluteY()
              + (int) coordinate.getX(), colors);
      }
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param list
   *          DOCUMENT ME!
   */
  private void updateFromVision(List<Tile> list) {
    if (list != null) {
      CharColor colors = new CharColor(CharColor.BLACK, CharColor.WHITE,
        CharColor.BOLD);
      Iterator<Tile> iterator = list.iterator();

      while (iterator.hasNext()) {
        Tile tile = iterator.next();
        Vector2D coordinate = tile.getCoordinate();
        TileType type = tile.getTileType();
        Marker marker = determineCreatureMarker(type);

        if (marker == null) {
          marker = determineLevelMarker(type);
        }

        Toolkit.printString(symbols.get(marker), getAbsoluteX()
            + (int) coordinate.getY(),
          getAbsoluteY() + (int) coordinate.getX(), colors);
      }
    }
  }

  /**
   * @see jcurses.widgets.Widget#handleInput(InputChar)
   */
  protected boolean handleInput(InputChar inputChar) {
    Integer code = new Integer(inputChar.getCode());
    Vector2D direction = directions.get(code);

    if (direction != null) {
      Vector2D coordinate = hero.getCoordinate();
      TileType type = plan.get(coordinate.add(direction));

      if (type instanceof Floor) {
        Floor floor = (Floor) type;
        Door door = floor.getDoor();

        if ((CommandType.CLOSE.equals(commandType)) && (door != null)
            && door.isOpen()) {
          command = new Command(commandType, door);
          commandReady = true;
        } else if ((CommandType.OPEN.equals(commandType)) && (door != null)
            && !door.isOpen()) {
          command = new Command(commandType, door);
          commandReady = true;
        } else if ((door == null) || door.isOpen()) {
          command = new Command(CommandType.MOVE, direction);
          commandReady = true;
        } else if ((door != null) && !door.isOpen()) {
          command = new Command(CommandType.OPEN, door);
          commandReady = true;
        } else {
          commandType = null;
          command = null;
          commandReady = false;
        }
      }
    } else {
      CommandType current = commandTypes.get(code);

      if (CommandType.UP.equals(current)) {
        command = new Command(current, null);
        commandReady = true;
      } else if (CommandType.DOWN.equals(current)) {
        command = new Command(current, null);
        commandReady = true;
      } else if (CommandType.QUIT.equals(current)) {
        command = new Command(current, null);
        commandReady = true;
      } else if (CommandType.WAIT.equals(current)) {
        command = new Command(current, null);
        commandReady = true;
      } else if (CommandType.INVENTORY.equals(current)) {
        Inventory inventory = new Inventory(hero);
        inventory.show();
      } else if (CommandType.SAVE.equals(current)) {
        command = new Command(current, null);
        commandReady = true;
      } else if (CommandType.PICKUP.equals(current)) {
        Vector2D coordinate = hero.getCoordinate();
        TileType type = plan.get(coordinate);

        if (type instanceof Floor) {
          Floor floor = (Floor) type;

          if (floor.getThingCount() > 0) {
            Thing thing = null;

            if (floor.getThingCount() == 1) {
              thing = (Thing) floor.getThings()
                .next();
            } else {
              Pickup pickup = new Pickup(floor);
              thing = pickup.selectThing();
            }

            if (thing != null) {
              command = new Command(current, thing);
              commandReady = true;
            }
          }
        }
      } else if (CommandType.DROP.equals(current)) {
        if (hero.getThings()
          .hasNext()) {
          Inventory inventory = new Inventory(hero);
          inventory.show();

          Thing thing = inventory.getSelected();

          if (thing != null) {
            command = new Command(current, thing);
            commandReady = true;
          }
        }
      } else {
        commandType = current;
        commandReady = false;
      }
    }

    if (commandReady) {
      Command result = command;
      commandType = null;
      command = null;
      commandReady = false;

      log(result.getType()
        .getName());
      hero.executeCommand(result);
    }

    return true;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param message
   *          DOCUMENT ME!
   */
  private void log(String message) {
    CharColor colors = new CharColor(CharColor.BLACK, CharColor.WHITE);
    Toolkit.printString(message, 0, 0, colors);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param type
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  private Marker determineCreatureMarker(TileType type) {
    Marker result = null;

    if ((type != null) && type instanceof Floor) {
      Floor floor = (Floor) type;
      Creature creature = floor.getCreature();

      if (creature == hero) {
        result = Marker.HERO;
      }
    }

    return result;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param type
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  private Marker determineLevelMarker(TileType type) {
    Marker result = null;

    if (type != null) {
      LinkedList<Object> objects = new LinkedList<Object>();
      objects.add(type);

      if (type instanceof Floor) {
        Floor floor = (Floor) type;

        if (floor.getPortal() != null) {
          objects.add(floor.getPortal());
        }

        if (floor.getDoor() != null) {
          objects.add(floor.getDoor());
        }

        Iterator<Thing> iterator = floor.getThings();

        while (iterator.hasNext()) {
          objects.add(iterator.next());
        }
      }

      Object top = objects.getLast();

      if (top instanceof Wall) {
        result = Marker.WALL;
      } else if (top instanceof Floor) {
        result = Marker.FLOOR;
      } else if (top instanceof Weapon) {
        result = Marker.WEAPON;
      } else if (top instanceof Door) {
        if (((Door) top).isOpen()) {
          result = Marker.DOOR_OPEN;
        } else {
          result = Marker.DOOR_CLOSED;
        }
      } else if (top instanceof Stairs) {
        if (((Stairs) top).getDirection()
          .equals(Stairs.DOWN)) {
          result = Marker.STAIRS_DOWN;
        } else {
          result = Marker.STAIRS_UP;
        }
      }
    }

    return result;
  }

  /**
   * @see jcurses.widgets.Widget#doPaint()
   */
  protected void doPaint() {
    doRepaint();
  }

  /**
   * @see jcurses.widgets.Widget#doRepaint()
   */
  protected void doRepaint() {
    updateFromPlan(null);
    updateFromVision(vision);
  }

  /**
   * @see jcurses.widgets.Widget#getPreferredSize()
   */
  protected Rectangle getPreferredSize() {
    return new Rectangle(0, 0, Toolkit.getScreenWidth(),
      Toolkit.getScreenHeight());
  }

  /**
   * DOCUMENT ME!
   */
  public void setGameEnd() {
    ActionEvent event = new ActionEvent(this);
    manager.handleEvent(event);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param listener
   *          DOCUMENT ME!
   */
  public void addActionListener(ActionListener listener) {
    manager.addListener(listener);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param listener
   *          DOCUMENT ME!
   */
  public void removeActionListener(ActionListener listener) {
    manager.removeListener(listener);
  }

  /**
   * @see jcurses.widgets.Widget#isFocusable()
   */
  protected boolean isFocusable() {
    return true;
  }
}