package ironfist;

import ironfist.geometry.Vector;

import java.util.LinkedList;
import java.util.List;


/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class BeamCastingVision {
  private int radius;
  private Level level;
  private int originX;
  private int originY;
  private List vision;

  /**
   * DOCUMENT ME!
   * 
   * @param x DOCUMENT ME!
   * @param y DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  private double distance(int x, int y) {
    return Math.max(x, y) + (Math.min(x, y) / 2);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param x DOCUMENT ME!
   * @param y DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  private boolean scanCell(int x, int y) {
    boolean result = false;
    Tile tile = level.get(new Vector(x, y));

    if (tile != null) {
      TileType type = tile.getTileType();

      if (type instanceof Floor) {
        Door door = ((Floor) type).getDoor();

        result = (door == null) || door.isOpen();
      }
    }

    return result;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param x DOCUMENT ME!
   * @param y DOCUMENT ME!
   */
  private void applyCell(int x, int y) {
    Tile tile = level.get(new Vector(x, y));

    if (tile != null) {
      vision.add(tile);
    }
  }

  /**
   * DOCUMENT ME!
   */
  public void castBeams() {
    int x = 32;
    int y = 32;

    boolean[][] trans1 = new boolean[x][y]; //to be the transparency map...
    boolean[][] visible1 = new boolean[x][y]; //to false...
    boolean[][] trans2 = new boolean[x][y]; //to be the transparency map...
    boolean[][] visible2 = new boolean[x][y]; //to false...
    boolean[][] trans3 = new boolean[x][y]; //to be the transparency map...
    boolean[][] visible3 = new boolean[x][y]; //to false...
    boolean[][] trans4 = new boolean[x][y]; //to be the transparency map...
    boolean[][] visible4 = new boolean[x][y]; //to false...

    // Build a circular border of opaque blocks so later
    // there won't be any need for extra code to check
    // against maximum range.
    //
    // Note that there are obvious optimizations to make
    // this step MUCH faster.
    for (x = 0; x <= radius; x++) {
      for (y = 0; y <= radius; y++) {
        if (distance(x, y) <= radius) {
          trans1[x][y] = scanCell(originX + x, originY + y);
          trans2[x][y] = scanCell(originX - x, originY + y);
          trans3[x][y] = scanCell(originX + x, originY - y);
          trans4[x][y] = scanCell(originX - x, originY - y);
        }
      }
    }


    // Set 0,0 to be visible even if the player is
    // standing on something opaque
    visible1[0][0] = true;

    // Check the orthogonal directions
    for (x = 1; trans1[x][0] && (x < radius); x++) {
      visible1[x][0] = true;
    }

    for (y = 1; trans1[0][y] && (y < radius); y++) {
      visible1[0][y] = true;
    }


    // Set 0,0 to be visible even if the player is
    // standing on something opaque
    visible2[0][0] = true;

    // Check the orthogonal directions
    for (x = 1; trans2[x][0] && (x < radius); x++) {
      visible2[x][0] = true;
    }

    for (y = 1; trans2[0][y] && (y < radius); y++) {
      visible2[0][y] = true;
    }


    // Set 0,0 to be visible even if the player is
    // standing on something opaque
    visible3[0][0] = true;

    // Check the orthogonal directions
    for (x = 1; trans3[x][0] && (x < radius); x++) {
      visible3[x][0] = true;
    }

    for (y = 1; trans3[0][y] && (y < radius); y++) {
      visible3[0][y] = true;
    }


    // Set 0,0 to be visible even if the player is
    // standing on something opaque
    visible4[0][0] = true;

    // Check the orthogonal directions
    for (x = 1; trans4[x][0] && (x < radius); x++) {
      visible4[x][0] = true;
    }

    for (y = 1; trans4[0][y] && (y < radius); y++) {
      visible4[0][y] = true;
    }

    // Now loop on the diagonal directions
    int v;
    int mini;
    int maxi;
    int u;
    int cor;

    for (int slope = 30; slope >= 2; slope--) {
      // initialize the v coordinate and set the beam size
      // to maximum--mini and maxi store the beam's current
      // top and bottom positions.
      // As long as mini<maxi, the beam has some width.
      // When mini=maxi, the beam is a thin line.
      // When mini>maxi, the beam has been blocked.
      v = 0;
      mini = 1;
      maxi = 32;

      //QUADRANT I
      for (u = 1; mini <= maxi; u++) { //loop on the u coordinate
        v += slope; //increment the beam's v coordinate
        y = v >> 5;
        x = u - y; //Do the transform
        cor = v & 31; //calculate the position of block corner within beam

        if (mini < cor) { //beam is low enough to hit (x,y) block
          visible1[x][y] = true;

          if (!trans1[x][y]) {
            mini = cor; //beam was partially blocked
          }
        }

        if (maxi > cor) { //beam is high enough to hit (x+1,y+1) block
          visible1[x + 1][y + 1] = true;

          if (!trans1[x + 1][y + 1]) {
            maxi = cor; //beam was partially blocked
          }
        }
      }

      v = 0;
      mini = 1;
      maxi = 32;

      //QUADRANT II
      for (u = 1; mini <= maxi; u++) { //loop on the u coordinate
        v += slope; //increment the beam's v coordinate
        y = v >> 5;
        x = u - y; //Do the transform
        cor = v & 31; //calculate the position of block corner within beam

        if (mini < cor) { //beam is low enough to hit (x,y) block
          visible2[x][y] = true;

          if (!trans2[x][y]) {
            mini = cor; //beam was partially blocked
          }
        }

        if (maxi > cor) { //beam is high enough to hit (x+1,y+1) block
          visible2[x + 1][y + 1] = true;

          if (!trans2[x + 1][y + 1]) {
            maxi = cor; //beam was partially blocked
          }
        }
      }

      v = 0;
      mini = 1;
      maxi = 32;

      //QUADRANT III
      for (u = 1; mini <= maxi; u++) { //loop on the u coordinate
        v += slope; //increment the beam's v coordinate
        y = v >> 5;
        x = u - y; //Do the transform
        cor = v & 31; //calculate the position of block corner within beam

        if (mini < cor) { //beam is low enough to hit (x,y) block
          visible3[x][y] = true;

          if (!trans3[x][y]) {
            mini = cor; //beam was partially blocked
          }
        }

        if (maxi > cor) { //beam is high enough to hit (x+1,y+1) block
          visible3[x + 1][y + 1] = true;

          if (!trans3[x + 1][y + 1]) {
            maxi = cor; //beam was partially blocked
          }
        }
      }

      v = 0;
      mini = 1;
      maxi = 32;

      //QUADRANT IV
      for (u = 1; mini <= maxi; u++) { //loop on the u coordinate
        v += slope; //increment the beam's v coordinate
        y = v >> 5;
        x = u - y; //Do the transform
        cor = v & 31; //calculate the position of block corner within beam

        if (mini < cor) { //beam is low enough to hit (x,y) block
          visible4[x][y] = true;

          if (!trans4[x][y]) {
            mini = cor; //beam was partially blocked
          }
        }

        if (maxi > cor) { //beam is high enough to hit (x+1,y+1) block
          visible4[x + 1][y + 1] = true;

          if (!trans4[x + 1][y + 1]) {
            maxi = cor; //beam was partially blocked
          }
        }
      }
    }

    for (x = 0; x <= radius; x++) {
      for (y = 0; y <= radius; y++) {
        if (distance(x, y) <= radius) {
          if (visible1[x][y]) {
            applyCell(originX + x, originY + y);
          }

          if (visible2[x][y]) {
            applyCell(originX - x, originY + y);
          }

          if (visible3[x][y]) {
            applyCell(originX + x, originY - y);
          }

          if (visible4[x][y]) {
            applyCell(originX - x, originY - y);
          }
        }
      }
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param map DOCUMENT ME!
   * @param x DOCUMENT ME!
   * @param y DOCUMENT ME!
   * @param maxRadius DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public List getSeen(Level map, int x, int y, int maxRadius) {
    level = map;
    originX = x;
    originY = y;
    radius = maxRadius;
    vision = new LinkedList();

    castBeams();

    return vision;
  }
}