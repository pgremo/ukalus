
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * DOCUMENT ME!
 * 
 * @version $Revision: 1.1 $
 * @author $author$
 */
public class BeamCastingFOVApplet extends Applet
    implements
      MouseListener,
      KeyListener {

  static int[] MASK = new int[16];

  static {
    for (int x = 15; (--x) >= 0;) {
      MASK[x] = (-1 - 7) >>> 1;
    }

    MASK[15] = 0;
  }

  int benchtime;
  int[] map = new int[32]; //The bitmap -- 1 means transparent
  int[] vis = new int[32]; //The visibility map
  int[] tmap1 = new int[16]; //the transformed transparency map
  int[] tvis1 = new int[16]; //the transformed visibility map
  int[] tmap2 = new int[16]; //Quadrant II
  int[] tvis2 = new int[16]; //Quadrant II
  int[] tmap3 = new int[16]; //Quadrant III
  int[] tvis3 = new int[16]; //Quadrant III
  int[] tmap4 = new int[16]; //Quadrant IV
  int[] tvis4 = new int[16]; //Quadrant IV

  /**
   * DOCUMENT ME!
   */
  void beamCast() {
    int starttime = (int) System.currentTimeMillis();

    for (int dum = 0; dum < 1000; dum++) {
      int x;
      int shif;
      int tempmask;
      int temp;

      //First, initialize transformed maps
      for (x = 16; (--x) >= 0;) {
        shif = 15 - x;
        tempmask = MASK[x];

        temp = tempmask & map[16 + x];
        tmap1[x] = temp >>> shif;
        tmap4[x] = temp << shif;

        temp = tempmask & map[16 - x];
        tmap2[x] = temp >>> shif;
        tmap3[x] = temp << shif;

        tvis1[x] = 0;
        tvis2[x] = 0;
        tvis3[x] = 0;
        tvis4[x] = 0;
      }

      //Second, cast the beams
      int mini;
      int maxi;
      int v;
      int mu;
      int cor;

      for (int slope = 31; (--slope) >= 2;) {
        // QUADRANT I beam
        maxi = 32;
        mini = 1;
        v = 0;

        for (mu = 4; mini <= maxi; mu <<= 1) { // mu is defined as 1<<u
          v += slope; // increment beam position
          x = v >> 5; // calculate horizontal position of beam
          cor = v & 31; // calculate corner position within beam

          if (cor < maxi) { // beam hits block at (x,u)
            tvis1[x] |= mu; // mark block at (x,u) visible

            if (0 == (tmap1[x] & mu)) {
              maxi = cor; // check for blocking
            }
          }

          if (cor > mini) { // beam hits block at (x+1,u)
            tvis1[x + 1] |= mu; // mark block at (x+1,u) visible

            if (0 == (tmap1[x + 1] & mu)) {
              mini = cor; // check for blocking
            }
          }
        } // end of beam throwing loop

        // QUADRANT II beam
        maxi = 32;
        mini = 1;
        v = 0;

        for (mu = 4; mini <= maxi; mu <<= 1) { // mu is defined as 1<<u
          v += slope; // increment beam position
          x = v >> 5; // calculate horizontal position of beam
          cor = v & 31; // calculate corner position within beam

          if (cor < maxi) { // beam hits block at (x,u)
            tvis2[x] |= mu; // mark block at (x,u) visible

            if (0 == (tmap2[x] & mu)) {
              maxi = cor; // check for blocking
            }
          }

          if (cor > mini) { // beam hits block at (x+1,u)
            tvis2[x + 1] |= mu; // mark block at (x+1,u) visible

            if (0 == (tmap2[x + 1] & mu)) {
              mini = cor; // check for blocking
            }
          }
        } // end of beam throwing loop

        // QUADRANT III beam
        maxi = 32;
        mini = 1;
        v = 0;

        for (mu = 1 << 30; mini <= maxi; mu >>>= 1) { // mu is defined as 1<<u
          v += slope; // increment beam position
          x = v >> 5; // calculate horizontal position of beam
          cor = v & 31; // calculate corner position within beam

          if (cor < maxi) { // beam hits block at (x,u)
            tvis3[x] |= mu; // mark block at (x,u) visible

            if (0 == (tmap3[x] & mu)) {
              maxi = cor; // check for blocking
            }
          }

          if (cor > mini) { // beam hits block at (x+1,u)
            tvis3[x + 1] |= mu; // mark block at (x+1,u) visible

            if (0 == (tmap3[x + 1] & mu)) {
              mini = cor; // check for blocking
            }
          }
        } // end of beam throwing loop

        // QUADRANT IV beam
        maxi = 32;
        mini = 1;
        v = 0;

        for (mu = 1 << 30; mini <= maxi; mu >>>= 1) { // mu is defined as 1<<u
          v += slope; // increment beam position
          x = v >> 5; // calculate horizontal position of beam
          cor = v & 31; // calculate corner position within beam

          if (cor < maxi) { // beam hits block at (x,u)
            tvis4[x] |= mu; // mark block at (x,u) visible

            if (0 == (tmap4[x] & mu)) {
              maxi = cor; // check for blocking
            }
          }

          if (cor > mini) { // beam hits block at (x+1,u)
            tvis4[x + 1] |= mu; // mark block at (x+1,u) visible

            if (0 == (tmap4[x + 1] & mu)) {
              mini = cor; // check for blocking
            }
          }
        } // end of beam throwing loop
      } // end of slope scanning loop

      // Now transform into normal coordinates
      for (x = 16; (--x) >= 0;) {
        shif = 15 - x;
        vis[16 + x] = (tvis1[x] << shif) | (tvis4[x] >>> shif);
        vis[16 - x] = (tvis2[x] << shif) | (tvis3[x] >>> shif);
      }

      vis[16] |= 65536; // paint center as visible
    }

    benchtime = (int) System.currentTimeMillis() - starttime;
  } // end of beamcast method

  /**
   * DOCUMENT ME!
   */
  public void init() {
    for (int x = 32; (--x) >= 0;) {
      map[x] = 0;
    }

    int startX = 16;
    int startY = 16;

    for (int i = 0; i < 5; i++) {
      int a = startX - (int) Math.floor((Math.random() * 5) + 1);
      int b = startX + (int) Math.floor((Math.random() * 5) + 1);
      int c = startY - (int) Math.floor((Math.random() * 5) + 1);
      int d = startY + (int) Math.floor((Math.random() * 5) + 1);

      for (int x = a; x <= b; x++) {
        for (int y = c; y <= d; y++) {
          map[x & 31] |= 1 << (y & 31);
        }
      }

      for (int j = 0; j < 5; j++) {
        if (1 == (int) Math.round(Math.random())) {
          if (1 == (int) Math.round(Math.random())) {
            for (int k = (int) Math.floor(Math.random() * 15); k > -3; --k) {
              startX++;
              map[startX & 31] |= 1 << (startY & 31);
            }
          } else {
            for (int k = (int) Math.floor(Math.random() * 15); k > -3; --k) {
              --startX;
              map[startX & 31] |= 1 << (startY & 31);
            }
          }
        } else if (1 == (int) Math.round(Math.random())) {
          for (int k = (int) Math.floor(Math.random() * 15); k > -3; --k) {
            startY++;
            map[startX & 31] |= 1 << (startY & 31);
          }
        } else {
          for (int k = (int) Math.floor(Math.random() * 15); k > -3; --k) {
            --startY;
            map[startX & 31] |= 1 << (startY & 31);
          }
        }
      }
    }

    for (int x = startX - 1; x <= (startX + 1); x++) {
      for (int y = startY - 1; y <= (startY + 1); y++) {
        map[x & 31] |= 1 << (y & 31);
      }
    }

    beamCast();
    this.addMouseListener(this);
    this.addKeyListener(this);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param g
   *          DOCUMENT ME!
   */
  public final synchronized void update(Graphics g) {
    paint(g);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param g
   *          DOCUMENT ME!
   */
  public void paint(Graphics g) {
    Color dark = new Color(17, 17, 17);
    beamCast();

    for (int x = 32; (--x) >= 0;) {
      for (int y = 32; (--y) >= 0;) {
        if (0 == (vis[x] & (1 << y))) {
          if (0 == (map[x] & (1 << y))) {
            g.setColor(dark);
          } else {
            g.setColor(Color.black);
          }
        } else if (0 == (map[x] & (1 << y))) {
          g.setColor(Color.cyan);
        } else {
          g.setColor(Color.blue);
        }

        g.fillRect(x << 4, (31 - y) << 4, 16, 16);
      }
    }

    g.setColor(Color.magenta);
    g.fillRect((16 << 4) + 1, ((31 - 16) << 4) + 1, 14, 14);
    g.setColor(Color.white);
    g.drawString("u", 257 - 64, 251 - 64);
    g.drawString("i", 257 + 00, 251 - 64);
    g.drawString("o", 257 + 64, 251 - 64);
    g.drawString("j", 257 - 64, 251 + 00);
    g.drawString("@", 257 + 00, 251 + 00);
    g.drawString("l", 257 + 64, 251 + 00);
    g.drawString("m", 257 - 64, 251 + 64);
    g.drawString(",", 257 + 00, 251 + 64);
    g.drawString(".", 257 + 64, 251 + 64);

    g.setColor(Color.yellow);
    g.drawString("Milliseconds: " + benchtime + "/1000", 16, 50);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void mouseClicked(MouseEvent e) {
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void mousePressed(MouseEvent e) {
    map[e.getX() >> 4] ^= 1 << (31 - (e.getY() >> 4));
    repaint();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void mouseReleased(MouseEvent e) {
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void mouseEntered(MouseEvent e) {
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void mouseExited(MouseEvent e) {
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void keyPressed(KeyEvent e) {
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void keyReleased(KeyEvent e) {
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void keyTyped(KeyEvent e) {
    int temp;
    char thekey = e.getKeyChar();

    switch (thekey) {
      case 'u' :
      case 'i' :
      case 'o' :

        //go up
        for (int x = 0; x < 32; x++) {
          map[x] = (map[x] >>> 1) | (map[x] << 31);
        }

        break;

      case 'm' :
      case ',' :
      case '.' :

        //go down
        for (int x = 0; x < 32; x++) {
          map[x] = (map[x] << 1) | (map[x] >>> 31);
        }

        break;
    }

    switch (thekey) {
      case 'u' :
      case 'j' :
      case 'm' :

        //go left
        temp = map[31];

        for (int x = 31; x > 0; --x) {
          map[x] = map[x - 1];
        }

        map[0] = temp;

        break;

      case 'o' :
      case 'l' :
      case '.' :

        //go right
        temp = map[0];

        for (int x = 0; x < 31; x++) {
          map[x] = map[x + 1];
        }

        map[31] = temp;

        break;
    }

    repaint();
  } // end of keyTyped method
} //end of class loslet
