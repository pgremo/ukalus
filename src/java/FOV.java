/*
The code in this sourse file implements line of sight algorithm
originally developed for Angband roguelike game (description of
this algorithm can be found in cave.c file in Angband sources):

A simple, fast, integer-based line-of-sight algorithm.  By Joseph Hall,
4116 Brewster Drive, Raleigh NC 27606.  Email to jnh@ecemwl.ncsu.edu.

The algorithm implementation included in this source code is written
from scratch by Serge Semashko to have a faster and better optimized
code (in Avanor LOS is used by EVERY monster, not only by the player,
so it is very important) and avoid problems with Angband license in
order to put this code under GPL license (so it is not a derived work
but independent algorithm implementation). The code which builds the
visibility table los_info[] is currently based on Angband sources, so
it is not included into Avanor distribution.

This implementations also has some nice features not present in the
original algorightm used for Angband:
1. It is possible to track whether the center of each cell is seen
2. Each grid is processed only once, it is very useful for implementing
        fireball spells using this code (the callback will be called only
        once for each grid, so none of them will be damaged twice)

*/
public class FOV {
  private static final int Y = 0;
  private static final int X = 1;
  private static final int[][][] GRID_DELTA = new int[][][] {
      {
        { 0, 0 },
        { 0, 0 },
        { 0, 0 },
        { 0, 0 },
        { 0, 0 },
        { 0, 0 },
        { 0, 0 },
        { 0, 0 }
      },
      {
        { 0, 1 },
        { 1, 0 },
        { 1, 0 },
        { 0, -1 },
        { 0, -1 },
        { -1, 0 },
        { -1, 0 },
        { 0, 1 }
      },
      {
        { 1, 1 },
        { 1, 1 },
        { 1, -1 },
        { 1, -1 },
        { -1, -1 },
        { -1, -1 },
        { -1, 1 },
        { -1, 1 }
      },
      {
        { 0, 2 },
        { 2, 0 },
        { 2, 0 },
        { 0, -2 },
        { 0, -2 },
        { -2, 0 },
        { -2, 0 },
        { 0, 2 }
      },
      {
        { 1, 2 },
        { 2, 1 },
        { 2, -1 },
        { 1, -2 },
        { -1, -2 },
        { -2, -1 },
        { -2, 1 },
        { -1, 2 }
      },
      {
        { 2, 2 },
        { 2, 2 },
        { 2, -2 },
        { 2, -2 },
        { -2, -2 },
        { -2, -2 },
        { -2, 2 },
        { -2, 2 }
      },
      {
        { 0, 3 },
        { 3, 0 },
        { 3, 0 },
        { 0, -3 },
        { 0, -3 },
        { -3, 0 },
        { -3, 0 },
        { 0, 3 }
      },
      {
        { 1, 3 },
        { 3, 1 },
        { 3, -1 },
        { 1, -3 },
        { -1, -3 },
        { -3, -1 },
        { -3, 1 },
        { -1, 3 }
      },
      {
        { 2, 3 },
        { 3, 2 },
        { 3, -2 },
        { 2, -3 },
        { -2, -3 },
        { -3, -2 },
        { -3, 2 },
        { -2, 3 }
      },
      {
        { 3, 3 },
        { 3, 3 },
        { 3, -3 },
        { 3, -3 },
        { -3, -3 },
        { -3, -3 },
        { -3, 3 },
        { -3, 3 }
      },
      {
        { 0, 4 },
        { 4, 0 },
        { 4, 0 },
        { 0, -4 },
        { 0, -4 },
        { -4, 0 },
        { -4, 0 },
        { 0, 4 }
      },
      {
        { 1, 4 },
        { 4, 1 },
        { 4, -1 },
        { 1, -4 },
        { -1, -4 },
        { -4, -1 },
        { -4, 1 },
        { -1, 4 }
      },
      {
        { 2, 4 },
        { 4, 2 },
        { 4, -2 },
        { 2, -4 },
        { -2, -4 },
        { -4, -2 },
        { -4, 2 },
        { -2, 4 }
      },
      {
        { 3, 4 },
        { 4, 3 },
        { 4, -3 },
        { 3, -4 },
        { -3, -4 },
        { -4, -3 },
        { -4, 3 },
        { -3, 4 }
      },
      {
        { 4, 4 },
        { 4, 4 },
        { 4, -4 },
        { 4, -4 },
        { -4, -4 },
        { -4, -4 },
        { -4, 4 },
        { -4, 4 }
      },
      {
        { 0, 5 },
        { 5, 0 },
        { 5, 0 },
        { 0, -5 },
        { 0, -5 },
        { -5, 0 },
        { -5, 0 },
        { 0, 5 }
      },
      {
        { 1, 5 },
        { 5, 1 },
        { 5, -1 },
        { 1, -5 },
        { -1, -5 },
        { -5, -1 },
        { -5, 1 },
        { -1, 5 }
      },
      {
        { 2, 5 },
        { 5, 2 },
        { 5, -2 },
        { 2, -5 },
        { -2, -5 },
        { -5, -2 },
        { -5, 2 },
        { -2, 5 }
      },
      {
        { 3, 5 },
        { 5, 3 },
        { 5, -3 },
        { 3, -5 },
        { -3, -5 },
        { -5, -3 },
        { -5, 3 },
        { -3, 5 }
      },
      {
        { 4, 5 },
        { 5, 4 },
        { 5, -4 },
        { 4, -5 },
        { -4, -5 },
        { -5, -4 },
        { -5, 4 },
        { -4, 5 }
      },
      {
        { 5, 5 },
        { 5, 5 },
        { 5, -5 },
        { 5, -5 },
        { -5, -5 },
        { -5, -5 },
        { -5, 5 },
        { -5, 5 }
      },
      {
        { 0, 6 },
        { 6, 0 },
        { 6, 0 },
        { 0, -6 },
        { 0, -6 },
        { -6, 0 },
        { -6, 0 },
        { 0, 6 }
      },
      {
        { 1, 6 },
        { 6, 1 },
        { 6, -1 },
        { 1, -6 },
        { -1, -6 },
        { -6, -1 },
        { -6, 1 },
        { -1, 6 }
      },
      {
        { 2, 6 },
        { 6, 2 },
        { 6, -2 },
        { 2, -6 },
        { -2, -6 },
        { -6, -2 },
        { -6, 2 },
        { -2, 6 }
      },
      {
        { 3, 6 },
        { 6, 3 },
        { 6, -3 },
        { 3, -6 },
        { -3, -6 },
        { -6, -3 },
        { -6, 3 },
        { -3, 6 }
      },
      {
        { 4, 6 },
        { 6, 4 },
        { 6, -4 },
        { 4, -6 },
        { -4, -6 },
        { -6, -4 },
        { -6, 4 },
        { -4, 6 }
      },
      {
        { 5, 6 },
        { 6, 5 },
        { 6, -5 },
        { 5, -6 },
        { -5, -6 },
        { -6, -5 },
        { -6, 5 },
        { -5, 6 }
      },
      {
        { 6, 6 },
        { 6, 6 },
        { 6, -6 },
        { 6, -6 },
        { -6, -6 },
        { -6, -6 },
        { -6, 6 },
        { -6, 6 }
      },
      {
        { 0, 7 },
        { 7, 0 },
        { 7, 0 },
        { 0, -7 },
        { 0, -7 },
        { -7, 0 },
        { -7, 0 },
        { 0, 7 }
      },
      {
        { 1, 7 },
        { 7, 1 },
        { 7, -1 },
        { 1, -7 },
        { -1, -7 },
        { -7, -1 },
        { -7, 1 },
        { -1, 7 }
      },
      {
        { 2, 7 },
        { 7, 2 },
        { 7, -2 },
        { 2, -7 },
        { -2, -7 },
        { -7, -2 },
        { -7, 2 },
        { -2, 7 }
      },
      {
        { 3, 7 },
        { 7, 3 },
        { 7, -3 },
        { 3, -7 },
        { -3, -7 },
        { -7, -3 },
        { -7, 3 },
        { -3, 7 }
      },
      {
        { 4, 7 },
        { 7, 4 },
        { 7, -4 },
        { 4, -7 },
        { -4, -7 },
        { -7, -4 },
        { -7, 4 },
        { -4, 7 }
      },
      {
        { 5, 7 },
        { 7, 5 },
        { 7, -5 },
        { 5, -7 },
        { -5, -7 },
        { -7, -5 },
        { -7, 5 },
        { -5, 7 }
      },
      {
        { 6, 7 },
        { 7, 6 },
        { 7, -6 },
        { 6, -7 },
        { -6, -7 },
        { -7, -6 },
        { -7, 6 },
        { -6, 7 }
      },
      {
        { 0, 8 },
        { 8, 0 },
        { 8, 0 },
        { 0, -8 },
        { 0, -8 },
        { -8, 0 },
        { -8, 0 },
        { 0, 8 }
      },
      {
        { 1, 8 },
        { 8, 1 },
        { 8, -1 },
        { 1, -8 },
        { -1, -8 },
        { -8, -1 },
        { -8, 1 },
        { -1, 8 }
      },
      {
        { 2, 8 },
        { 8, 2 },
        { 8, -2 },
        { 2, -8 },
        { -2, -8 },
        { -8, -2 },
        { -8, 2 },
        { -2, 8 }
      },
      {
        { 3, 8 },
        { 8, 3 },
        { 8, -3 },
        { 3, -8 },
        { -3, -8 },
        { -8, -3 },
        { -8, 3 },
        { -3, 8 }
      },
      {
        { 4, 8 },
        { 8, 4 },
        { 8, -4 },
        { 4, -8 },
        { -4, -8 },
        { -8, -4 },
        { -8, 4 },
        { -4, 8 }
      },
      {
        { 5, 8 },
        { 8, 5 },
        { 8, -5 },
        { 5, -8 },
        { -5, -8 },
        { -8, -5 },
        { -8, 5 },
        { -5, 8 }
      },
      {
        { 0, 9 },
        { 9, 0 },
        { 9, 0 },
        { 0, -9 },
        { 0, -9 },
        { -9, 0 },
        { -9, 0 },
        { 0, 9 }
      },
      {
        { 1, 9 },
        { 9, 1 },
        { 9, -1 },
        { 1, -9 },
        { -1, -9 },
        { -9, -1 },
        { -9, 1 },
        { -1, 9 }
      },
      {
        { 2, 9 },
        { 9, 2 },
        { 9, -2 },
        { 2, -9 },
        { -2, -9 },
        { -9, -2 },
        { -9, 2 },
        { -2, 9 }
      },
      {
        { 3, 9 },
        { 9, 3 },
        { 9, -3 },
        { 3, -9 },
        { -3, -9 },
        { -9, -3 },
        { -9, 3 },
        { -3, 9 }
      }
    };
  private static FOVInfo[] INFO = new FOVInfo[] {
      new FOVInfo(GRID_DELTA[0], 0x00000000, 0x00000001, 2, 2, 0, 1),
      new FOVInfo(GRID_DELTA[1], 0x7FFFFFFF, 0x00000001, 3, 4, 1, 1),
      new FOVInfo(GRID_DELTA[2], 0xFFFF8000, 0x80000000, 5, 5, 1, 2),
      new FOVInfo(GRID_DELTA[3], 0x00003FFF, 0x00000001, 6, 7, 2, 1),
      new FOVInfo(GRID_DELTA[4], 0x7FFFFC00, 0x00180000, 7, 8, 2, 4),
      new FOVInfo(GRID_DELTA[5], 0xFF000000, 0x80000000, 9, 9, 3, 2),
      new FOVInfo(GRID_DELTA[6], 0x000001FF, 0x00000001, 10, 11, 3, 1),
      new FOVInfo(GRID_DELTA[7], 0x007FFF80, 0x00004000, 11, 12, 3, 4),
      new FOVInfo(GRID_DELTA[8], 0x7FFC0000, 0x03000000, 12, 13, 4, 4),
      new FOVInfo(GRID_DELTA[9], 0xF8000000, 0x80000000, 14, 14, 4, 2),
      new FOVInfo(GRID_DELTA[11], 0x0000003F, 0x00000001, 15, 16, 4, 1),
      new FOVInfo(GRID_DELTA[12], 0x0001FFC0, 0x00000C00, 16, 17, 4, 4),
      new FOVInfo(GRID_DELTA[13], 0x03FF8000, 0x00180000, 17, 18, 4, 4),
      new FOVInfo(GRID_DELTA[14], 0x7F800000, 0x18000000, 18, 19, 5, 4),
      new FOVInfo(GRID_DELTA[15], 0xE0000000, 0x80000000, 20, 20, 6, 2),
      new FOVInfo(GRID_DELTA[16], 0x0000001F, 0x00000001, 21, 22, 5, 1),
      new FOVInfo(GRID_DELTA[17], 0x00003FE0, 0x00000200, 22, 23, 5, 4),
      new FOVInfo(GRID_DELTA[18], 0x003FE000, 0x00018000, 23, 24, 5, 4),
      new FOVInfo(GRID_DELTA[19], 0x0FF80000, 0x00800000, 24, 25, 6, 4),
      new FOVInfo(GRID_DELTA[20], 0x7E000000, 0x30000000, 25, 26, 6, 4),
      new FOVInfo(GRID_DELTA[21], 0xC0000000, 0x80000000, 27, 27, 7, 2),
      new FOVInfo(GRID_DELTA[22], 0x0000000F, 0x00000001, 28, 29, 6, 1),
      new FOVInfo(GRID_DELTA[23], 0x00000FF0, 0x00000180, 29, 30, 6, 4),
      new FOVInfo(GRID_DELTA[34], 0x0003F800, 0x00004000, 30, 31, 6, 4),
      new FOVInfo(GRID_DELTA[25], 0x00FF0000, 0x00180000, 31, 32, 7, 4),
      new FOVInfo(GRID_DELTA[26], 0x1FC00000, 0x03000000, 32, 33, 7, 4),
      new FOVInfo(GRID_DELTA[27], 0x7C000000, 0x60000000, 33, 34, 8, 4),
      new FOVInfo(GRID_DELTA[28], 0x80000000, 0x80000000, 0, 0, 8, 2),
      new FOVInfo(GRID_DELTA[29], 0x00000007, 0x00000001, 35, 36, 7, 1),
      new FOVInfo(GRID_DELTA[30], 0x000003F8, 0x00000040, 36, 37, 7, 4),
      new FOVInfo(GRID_DELTA[31], 0x00007C00, 0x00003000, 37, 38, 7, 4),
      new FOVInfo(GRID_DELTA[32], 0x001F8000, 0x00020000, 38, 39, 8, 4),
      new FOVInfo(GRID_DELTA[33], 0x01F00000, 0x00C00000, 39, 40, 8, 4),
      new FOVInfo(GRID_DELTA[34], 0x3F000000, 0x04000000, 40, 0, 9, 4),
      new FOVInfo(GRID_DELTA[35], 0x70000000, 0xC0000000, 0, 0, 9, 4),
      new FOVInfo(GRID_DELTA[36], 0x00000003, 0x00000001, 41, 42, 8, 1),
      new FOVInfo(GRID_DELTA[37], 0x000001FC, 0x00000060, 42, 43, 8, 4),
      new FOVInfo(GRID_DELTA[38], 0x00003E00, 0x00000C00, 43, 44, 8, 4),
      new FOVInfo(GRID_DELTA[39], 0x0007C000, 0x0000C000, 44, 0, 9, 4),
      new FOVInfo(GRID_DELTA[40], 0x007E0000, 0x00180000, 0, 0, 9, 4),
      new FOVInfo(GRID_DELTA[41], 0x07E00000, 0x01800000, 0, 0, 9, 4),
      new FOVInfo(GRID_DELTA[42], 0x00000001, 0x00000001, 0, 0, 9, 1),
      new FOVInfo(GRID_DELTA[43], 0x000000FE, 0x00000020, 0, 0, 9, 4),
      new FOVInfo(GRID_DELTA[44], 0x00001F00, 0x00000600, 0, 0, 9, 4),
      new FOVInfo(GRID_DELTA[45], 0x0000F000, 0x00004000, 0, 0, 9, 4)
    };

  //	 We prepared the grid data so that we need only 32 bits for slopes mask, it fits just  
  //	 one variable
  private static final int BIT_MASK0 = 0xFFFFFFFF;

  private static final int MAX_SIGHT = getMaxSight();

  static private int getMaxSight() {
    int distance = 0;

    for (int index = 0; index < INFO.length; index++) {
      if (INFO[index].distance > distance) {
        distance = INFO[index].distance;
      }

      if (INFO[index].next0 == INFO[index].next1) {
        INFO[index].next1 = 0;
      }
    }

    return distance;
  }

  public void LineOfSight(int x, int y, int radius, Scanner scanner) {
    if (radius > MAX_SIGHT) {
      radius = MAX_SIGHT;
    }

    scanner.scan(x, y, 0, true);

    //		Scan each octant
    for (int o = 0; o < 8; o++) {
      //  Initialize horizontal/vertical and diagonal usage flagmask 
      //  (used in order not to call grid_callback function twice)
      int mask = (int) ((2L >> (o % 2)) | 4);

      if (o == 0) {
        mask |= 3;
      }

      if (o == 7) {
        mask &= ~3;
      }

      //  Angbandish trick, very useful for avoiding coming to the same grid twice
      int last = 0;

      //  Visibility bit vector, initially all the directions are visible
      long visibility = BIT_MASK0;

      //  Grid queue
      FOVInfo[] queue = new FOVInfo[INFO.length * 4];
      int head = 0;
      int tail = 0;

      //  Initial grids
      queue[tail++] = INFO[1];
      queue[tail++] = INFO[2];

      //  Process queue
      while (head < tail) {
        //  Extract next grid los info
        FOVInfo current = queue[head++];

        //  Check radius and bits
        if (((visibility & (current.bits0)) != 0) &&
              (current.distance <= radius)) {
          boolean isVisible = true;

          //  For grids, disabled in flagmask, take their visibility value from cache
          //  (the flags are chosen so that this value should already be there)
          if ((current.flag & mask) != 0) {
            isVisible = scanner.scan(x + current.delta[o][X],
                y + current.delta[o][Y], current.distance,
                ((visibility & current.bits0_c) == current.bits0_c));
          }

          if (isVisible) {
            //  For each visible grid, put into a queue the next grids which
            //  probably can be seen behind this one, there are up to two such grids
            if ((last != current.next0) && (current.next0 != 0)) {
              queue[tail++] = INFO[current.next0];
            }

            if (current.next1 != 0) {
              queue[tail++] = INFO[last = current.next1];
            }
          } else {
            //  Remove visibility bit flags from for the directions this solid grid hides
            visibility &= ~(current.bits0);
          }
        }
      }
    }
  }

  private static class FOVInfo {
    int[][] delta;
    long bits0;
    long bits0_c;
    int next0;
    int next1;
    int distance;
    int flag;

    public FOVInfo(int[][] delta, long bits0, long bits0_c, int next0,
      int next1, int distance, int flag) {
      this.delta = delta;
      this.bits0 = bits0;
      this.bits0_c = bits0_c;
      this.next0 = next0;
      this.next1 = next1;
      this.distance = distance;
      this.flag = flag;
    }
  }
}
