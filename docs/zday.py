#!/usr/bin/env python

import sys, curses, time, random, math

# --- User preferences data --------
margin = 5
command_keys = {
    "KEY_UP":      ("go", ( 0,-1)),
    "KEY_DOWN":    ("go", ( 0, 1)),
    "KEY_LEFT":    ("go", (-1, 0)),
    "KEY_RIGHT":   ("go", ( 1, 0)),
    "KEY_A1":      ("go", (-1,-1)),
    "KEY_A3":      ("go", ( 1,-1)),
    "KEY_C1":      ("go", (-1, 1)),
    "KEY_C3":      ("go", ( 1, 1)),

    '8':           ("go", ( 0,-1)),
    '2':           ("go", ( 0, 1)),
    '4':           ("go", (-1, 0)),
    '6':           ("go", ( 1, 0)),
    '7':           ("go", (-1,-1)),
    '9':           ("go", ( 1,-1)),
    '1':           ("go", (-1, 1)),
    '3':           ("go", ( 1, 1)),

    'a':           ("use", 0),
    'b':           ("use", 1),
    'c':           ("use", 2),
    'd':           ("use", 3),
    'e':           ("use", 4),
    'f':           ("use", 5),
    'g':           ("use", 6),
    'h':           ("use", 7),
    'i':           ("use", 8),
    'j':           ("use", 9),
    'k':           ("use", 10),
    'l':           ("use", 11),
    'm':           ("use", 12),
    'n':           ("use", 13),
    'o':           ("use", 14),
    'p':           ("use", 15),
    'q':           ("use", 16),
    'r':           ("use", 17),
    's':           ("use", 18),
    't':           ("use", 19),
    'u':           ("use", 20),

    'A':           ("trash", 0),
    'B':           ("trash", 1),
    'C':           ("trash", 2),
    'D':           ("trash", 3),
    'E':           ("trash", 4),
    'F':           ("trash", 5),
    'G':           ("trash", 6),
    'H':           ("trash", 7),
    'I':           ("trash", 8),
    'J':           ("trash", 9),
    'K':           ("trash", 10),
    'L':           ("trash", 11),
    'M':           ("trash", 12),
    'N':           ("trash", 13),
    'O':           ("trash", 14),
    'P':           ("trash", 15),
    'Q':           ("trash", 16),
    'R':           ("trash", 17),
    'S':           ("trash", 18),
    'T':           ("trash", 19),
    'U':           ("trash", 20),

    "KEY_B2":      ("wait", None),
    '5':           ("wait", None),

    'v':           ("run", None),
    'y':           ("loot", None),
    'z':           ("shoot", None),
    'x':           ("aim", 1),
    'X':           ("aim", -1),
    '>':           ("operate", None),
    '<':           ("operate", None),
    
    '!':           ("quit", None),
}
# --- End of user preferences data -

# --- World data -------------------
tiles = (
    {
        "name":     'floor',
        "visible":  ord('.') | curses.A_BOLD,
        "explored": ord('.'),
        "shadow":   ord('.') | curses.A_DIM,
        "block":    0,
        "opaque":   0,
        "item":     0,
        "change":   4,
    }, {
        "name":     'wall',
        "visible":  ord('#') | curses.A_BOLD,
        "explored": ord('#'),
        "block":    1,
        "opaque":   1,
        "item":     0,
        "change":   5,
    }, {
        "name":     'corpse',
        "visible":  ord('%'),
        "explored": ord('%'),
        "block":    0,
        "opaque":   0,
        "item":     1,
        "change":   3,
    }, {
        "name":     'rotten corpse',
        "visible":  ord('='),
        "explored": ord('='),
        "block":    0,
        "opaque":   0,
        "item":     0,
    }, {
        "name":     'ruined floor',
        "visible":  ord(':') | curses.A_BOLD,
        "explored": ord(':'),
        "block":    0,
        "opaque":   0,
        "item":     0,
        "change":   0,
    }, {
        "name":     'ruined wall',
        "visible":  ord('#'),
        "explored": ord('#') | curses.A_DIM,
        "block":    1,
        "opaque":   1,
        "item":     0,
        "change":   4,
    }, {
        "name":     'blood pool',
        "visible":  ord(':') | curses.A_BOLD,
        "explored": ord(':'),
        "block":    0,
        "opaque":   0,
        "item":     0,
        "change":   0,
    }, {
        "name":     'blood stain',
        "visible":  ord('.') | curses.A_BOLD,
        "explored": ord('.'),
        "block":    0,
        "opaque":   0,
        "item":     0,
        "change":   0,
    }, {
        "name":     'bloody wall',
        "visible":  ord('#') | curses.A_BOLD,
        "explored": ord('#'),
        "block":    1,
        "opaque":   1,
        "item":     0,
        "change":   5,
    }, {
        "name":     'crate',
        "visible":  ord('&') | curses.A_BOLD,
        "explored": ord('&'),
        "block":    0,
        "opaque":   0,
        "item":     1,
        "change":   4,
    }, {
        "name":     'elevator down',
        "visible":  ord('>') | curses.A_BOLD,
        "explored": ord('>') | curses.A_BOLD,
        "block":    0,
        "opaque":   0,
        "item":     0,
        "operate":   1,
    }, {
        "name":     'elevator up',
        "visible":  ord('<') | curses.A_BOLD,
        "explored": ord('<') | curses.A_BOLD,
        "block":    0,
        "opaque":   0,
        "item":     0,
        "operate":   1,
    }, {
        "name":     'closed door',
        "visible":  ord('+'),
        "explored": ord('+'),
        "block":    1,
        "opaque":   1,
        "item":     0,
        "change":   13,
        "operate":   1,
    }, {
        "name":     'open door',
        "visible":  ord("'"),
        "explored": ord("'"),
        "block":    0,
        "opaque":   0,
        "item":     0,
        "change":   12,
        "operate":   1,
    },
)
MAP_FLOOR =     0
MAP_WALL =      1
MAP_CORPSE =    2
MAP_BLOOD =     6
MAP_CRATE =     9
MAP_ELEV =      10
MAP_DOOR =      12

FLAG_EXPLORED = 1
FLAG_VISIBLE =  2
FLAG_CREATURE = 4
FLAG_ITEM =     8
FLAG_BLOCKED =  16
FLAG_OPAQUE =   32
FLAG_NONE =     0

KIND_WEAPON =   0
KIND_GUN =      1

KIND_AMMO =     2
KIND_FOOD =     3
KIND_TRASH =    4

items = (
    {   "name":     '---',
        "kind":     KIND_TRASH,
        "prob":     0,
    },
    {   "name":     'steel pipe',
        "kind":     KIND_WEAPON,
        "prob":     8,
        "accuracy": 50,
        "sound":    "Bash",
        "blood":    20,
    },
    {   "name":     'axe',
        "kind":     KIND_WEAPON,
        "prob":     6,
        "accuracy": 80,
        "sound":    "Hack",
        "push":     1,
        "blood":    60,
    },
    {   "name":     'blowtorch',
        "kind":     KIND_WEAPON,
        "charges":  6,
        "prob":     3,
        "accuracy": 70,
        "sound":    "Sslsh",
    },
    {   "name":     'chainsaw',
        "kind":     KIND_WEAPON,
        "prob":     1,
        "charges":  6,
        "sound":    "Wrrrooaaam!",
        "noisy":    1,
        "push":     1,
        "blood":    200,
    },
    {   "name":     'revolver',
        "kind":     KIND_GUN,
        "prob":     8,
        "charges":  6,
        "sound":    "Bang!",
        "noisy":    1,
        "blood":    40,
    },
    {   "name":     'nail gun',
        "kind":     KIND_GUN,
        "prob":     12,
        "charges":  24,
        "sound":    "Zing!",
        "blood":    20,
    },
    {   "name":     'shotgun',
        "kind":     KIND_GUN,
        "prob":     5,
        "charges":  8,
        "sound":    "Blam!",
        "push":     1,
        "noisy":    1,
        "blood":    100,
    },
    {   "name":     'molotov',
        "kind":     KIND_GUN,
        "prob":     20,
        "sound":    "Swoosh!",
    },
    {   "name":     'chocolates',
        "kind":     KIND_FOOD,
        "prob":     20,
        "charges":  8,
        "food":     10,
        "sound":    "Yummy, my favorite.",
    },
    {   "name":     'ration',
        "kind":     KIND_FOOD,
        "prob":     30,
        "food":     40,
        "sound":    "Munch, munch!",
    },
    {   "name":     'med pack',
        "kind":     KIND_FOOD,
        "prob":     15,
        "heal":     3,
        "sound":    "Much beter!",
    },
    {   "name":     'wound spray',
        "kind":     KIND_FOOD,
        "prob":     20,
        "heal":     1,
        "charges":  4,
        "sound":    "Psshh!",
    },
)
item_maxprob = 0
for item in items:
    item_maxprob += item["prob"]
        

# --- End of world data ------------


def distance(a, b):
    x1, y1 = a
    x2, y2 = b
    return max(abs(x2-x1), abs(y2-y1))

class GameError(Exception):
    pass

class ActError(Exception):
    pass

class Log:
    def __init__(self):
        self.file = open("zday.log", "a")

    def write(self, msg):
        self.file.write("%s: %s\n" % (time.ctime(), msg))
        self.file.flush()

    def __del__(self):
        self.file.close()

class Mesg:
    def __init__(self):
        self.mesgs = []
        self.last = []
        self.history = []

    def clear(self):
        self.last = self.mesgs
        self.history += self.mesgs
        self.mesgs = []

    def write(self, msg):
        self.mesgs.append(msg)

    def draw(self, disp):
        disp.msg.clear()
        for msg in self.last:
            disp.msg.addstr(msg, curses.color_pair(2))
            disp.msg.addstr(" ")
        for msg in self.mesgs:
            disp.msg.addstr(msg, curses.A_BOLD | curses.color_pair(2))
            disp.msg.addstr(" ")

    def output(self):
        for msg in self.mesgs:
            print msg


class Effect:
    def __init__(self):
        self.effects = []
        self.eff_back = []
        
    def add(self, pos, char):
        self.effects.append((pos, char, None))

    def clear(self):
        self.effects = []
    
    def draw(self, disp):
        self.eff_back = []
        for i in range(len(self.effects)):
            pos, char, clear = self.effects[i]
            x, y = pos
            self.eff_back.append(1)
            clear = disp.map.inch(y-disp.ymap, x-disp.xmap)
            disp.mapdraw(x, y, char)
            self.effects[i] = pos, char, clear

    def undraw(self, disp):
        for e in self.effects:
            pos, char, clear = e
            x, y = pos
            disp.mapdraw(x, y, clear)
            

# The Angband's algo, gives some strange behavior, but is simple.
class Light:
    def __init__(self, world):
        self.world = world
        self.lit = []
        self.last = []
        maxradius = 16 
        self.maxrays = 255
        self.radius = 8
        self.ray_max = []
        self.ray_min = []
        for y in range(maxradius):
            line_max = []
            line_min = []
            for x in range(maxradius):
                line_max.append(0)
                line_min.append(self.maxrays)
            self.ray_max.append(line_max)
            self.ray_min.append(line_min)
        for ray in range(self.maxrays):
            an = (math.pi*ray)/(self.maxrays*2)
            s = math.sin(an)/4
            c = math.cos(an)/4
            d =0
            x =0
            y =0
            while 1:
                x = int(math.floor(s*d))
                y = int(math.floor(c*d))
                d += 1
                if x<self.radius and y<self.radius:
                    self.ray_min[x][y] = min(self.ray_min[x][y], ray)
                    self.ray_max[x][y] = max(self.ray_max[x][y], ray)
                else:
                    break

    def _quart(self, xpos, ypos, dx, dy):
        rays = []
        ox = xpos
        for i in range(self.maxrays):
            rays.append(0)
        corner = self.radius
        for y in range(self.radius):
            if (2*y>=self.radius):
                corner -= 1
            for x in range(corner):
                opaque = self.world.flags[xpos][ypos] & FLAG_OPAQUE
                shadow = 0
                rayweight = self.maxrays / (self.ray_max[x][y] - self.ray_min[x][y])
                for ray in range(self.ray_min[x][y], self.ray_max[x][y]):
                    if rays[ray]:
                        shadow += rayweight
                if 3*shadow<self.maxrays:
                    self.lit.append((xpos, ypos))
                    self.world.flags[xpos][ypos] |= FLAG_VISIBLE | FLAG_EXPLORED
                if opaque:
                    for ray in range(self.ray_min[x][y], self.ray_max[x][y]):
                        rays[ray] = 1
                xpos += dx
                if xpos<0 or xpos>=self.world.width:
                    break
            ypos += dy
            if ypos<0 or ypos>=self.world.width:
                break
            xpos = ox
                
    def cast(self, xpos, ypos):
        self.last = self.lit
        r = self.radius
        self.lit = [(xpos,ypos)]
        self._quart(xpos, ypos, 1, 1)
        self._quart(xpos, ypos, -1, 1)
        self._quart(xpos, ypos, 1, -1)
        self._quart(xpos, ypos, -1, -1)
        for (x,y) in self.last:
            if not (x,y) in self.lit:
                self.world.flags[x][y] &= ~FLAG_VISIBLE


class World:
    def __init__(self):
        self.width, self.height = 64, 64
        self.hero = None
        self.new_level()

    def new_level(self):
        self.map = []
        self.flags = []
        for x in range(self.width):
            line_map = []
            line_flags = []
            for y in range(self.height):
                line_map.append(MAP_FLOOR)
                line_flags.append(FLAG_NONE)
            self.map.append(line_map)
            self.flags.append(line_flags)
        self.generate()
        for x in range(self.width):
            self.add_tile((x, 0), MAP_WALL)
            self.add_tile((x, self.height-1), MAP_WALL)
        for y in range(self.width):
            self.add_tile((0, y), MAP_WALL)
            self.add_tile((self.width-1, y), MAP_WALL)
        self.decorate()
        self.erode()
        if self.hero:
            self.creatures = [self.hero]
        else:
            self.creatures = []
        self.populate()


    def find_creature(self, pos):
        for creat in self.creatures:
            if creat.pos == pos:
                return creat
        return None

    def add_tile(self, pos, tile):
        x,y = pos
        if x<0 or y<0 or x>=self.width or y>=self.height:
            return
        self.map[x][y] = tile
        if tiles[tile]['block']:
            self.flags[x][y] |= FLAG_BLOCKED
        else:
            self.flags[x][y] &= ~FLAG_BLOCKED
        if tiles[tile]['opaque']:
            self.flags[x][y] |= FLAG_OPAQUE
        else:
            self.flags[x][y] &= ~FLAG_OPAQUE
        if tiles[tile]['item']:
            self.flags[x][y] |= FLAG_ITEM
        else:
            self.flags[x][y] &= ~FLAG_ITEM
        
    def add_rect(self, topleft, bottomright, tile):
        x1, y1 = topleft
        x2, y2 = bottomright
        for y in range(y1, y2):
            self.add_tile((x1, y), tile)
            self.add_tile((x2, y), tile)
        for x in range(x1, x2):
            self.add_tile((x, y1), tile)
            self.add_tile((x, y2), tile)

    def decorate(self):
        for i in range(random.randrange(5, 20)):
            x = random.randrange(1, self.width-2)
            y = random.randrange(1, self.width-2)
            self.add_tile((x, y), MAP_CORPSE)
        for i in range(random.randrange(20, 40)):
            x = random.randrange(1, self.width-2)
            y = random.randrange(1, self.width-2)
            self.add_tile((x, y), MAP_CRATE)
        for i in range(5):
            while 1:
                x = random.randrange(1, self.width-2)
                y = random.randrange(1, self.width-2)
                if self.map[x][y] == MAP_FLOOR:
                    self.add_tile((x, y), MAP_ELEV)
                    break
        if self.hero:
            x,y = self.hero.pos
            self.add_tile((x, y), MAP_ELEV + 1)


    def erode(self):
        for i in range(random.randrange(350, 700)):
            x = random.randrange(1, self.width-2)
            y = random.randrange(1, self.width-2)
            self.change((x, y))

    def populate(self):
        for i in range(random.randrange(50, 90)):
            Zombie(self)

    def generate(self):
        for i in range(200):
            x1 = random.randrange(1, self.width-2)
            y1 = random.randrange(1, self.height-2)
            x2 = min(random.randrange(x1+1, self.width-1), x1+10)
            y2 = min(random.randrange(y1+1, self.height-1), y1+10)
            self.add_rect((x1+1,y1+1), (x2-1,y2-1), MAP_FLOOR)
            self.add_rect((x1,y1), (x2,y2), MAP_WALL)
            self.add_rect((x1-1,y1-1), (x2+1,y2+1), MAP_FLOOR)
            for j in range(random.randrange(1, 5)):
                tile = random.choice((MAP_FLOOR,MAP_DOOR,MAP_DOOR+1))
                if random.choice((0,1)):
                    if x2-x1>2:
                        x = random.randrange(x1+1, x2-1)
                        if random.choice((0,1)):
                            y = y1
                        else:
                            y = y2
                        self.add_tile((x, y), tile)
                else:
                    if y2-y1>2:
                        y = random.randrange(y1+1, y2-1)
                        if random.choice((0,1)):
                            x = x1
                        else:
                            x = x2
                        self.add_tile((x, y), tile)

    def change(self, pos):
        x, y = pos
        try:
            changed = tiles[self.map[x][y]]["change"]
        except KeyError:
            return
        self.add_tile(pos, changed)

    def noise(self):
        for creat in self.creatures:
            if distance(creat.pos, self.hero.pos)<10:
                if not creat.target:
                    creat.target = self.hero.pos
        

class Display:
    def __init__(self, stdscr):
        maxy, maxx = stdscr.getmaxyx()
        self.msg = curses.newwin(1, maxx-16, 0, 0)
        self.inv = curses.newwin(maxy-1, 16, 1, maxx-16)
        self.stat = curses.newwin(1, 16, 0, maxx-16)
        self.map = curses.newwin(maxy-1, maxx-16, 1, 0)
        self.map.keypad(1)
        self.map.immedok(0)
        self.inv.immedok(0)
        self.stat.immedok(0)
        self.msg.immedok(0)
        self.stat.scrollok(0)
        self.msg.scrollok(1)
        self.xmap = 0
        self.ymap = 0
        self.maxmapy, self.maxmapx = self.map.getmaxyx()
        curses.meta(1)
        self.map.leaveok(1)
        curses.curs_set(0)
        self.effects = []
        self.eff_back = []
        if curses.has_colors():
            #curses.use_default_colors()
            curses.init_pair(1, curses.COLOR_BLACK, curses.COLOR_BLACK)
            curses.init_pair(2, curses.COLOR_GREEN, curses.COLOR_BLACK)
            curses.init_pair(3, curses.COLOR_YELLOW, curses.COLOR_BLACK)
            curses.init_pair(4, curses.COLOR_MAGENTA, curses.COLOR_BLACK)
            curses.init_pair(5, curses.COLOR_RED, curses.COLOR_BLACK)
            curses.init_pair(6, curses.COLOR_CYAN, curses.COLOR_BLACK)

            tiles[0]['visible'] = ord('.')
            tiles[0]['explored'] = ord('.') | curses.color_pair(4)
            tiles[0]['shadow'] = ord('.') | curses.color_pair(1) | curses.A_BOLD

            tiles[1]['visible'] = ord('#')
            tiles[1]['explored'] = ord('#') | curses.color_pair(4)

            tiles[2]['visible'] = ord('%') | curses.color_pair(5) | curses.A_BOLD
            tiles[2]['explored'] = ord('%') | curses.color_pair(4) | curses.A_BOLD

            tiles[3]['visible'] = ord('=') | curses.color_pair(5)
            tiles[3]['explored'] = ord('=') | curses.color_pair(4)

            tiles[4]['visible'] = ord(':') | curses.color_pair(3)
            tiles[4]['explored'] = ord(':') | curses.color_pair(4)

            tiles[5]['visible'] = ord('#') | curses.color_pair(1) | curses.A_BOLD
            tiles[5]['explored'] = ord('#') | curses.color_pair(4)
            
            tiles[6]['visible'] = ord(';') | curses.color_pair(5)
            tiles[6]['explored'] = ord(';') | curses.color_pair(4)
            
            tiles[7]['visible'] = ord('.') | curses.color_pair(5)
            tiles[7]['explored'] = ord('.') | curses.color_pair(4)
            
            tiles[8]['visible'] = ord('#') | curses.color_pair(5) | curses.A_BOLD
            tiles[8]['explored'] = ord('#') | curses.color_pair(4)

            tiles[9]['visible'] = ord('&') | curses.color_pair(3) | curses.A_BOLD
            tiles[9]['explored'] = ord('&') | curses.color_pair(4) | curses.A_BOLD
            
            tiles[10]['visible'] = ord('>') | curses.color_pair(3) | curses.A_BOLD
            tiles[10]['explored'] = ord('>') | curses.color_pair(4) | curses.A_BOLD
            
            tiles[11]['visible'] = ord('<') | curses.color_pair(3) | curses.A_BOLD
            tiles[11]['explored'] = ord('<') | curses.color_pair(4) | curses.A_BOLD

            tiles[12]['visible'] = ord('+') | curses.color_pair(6)
            tiles[12]['explored'] = ord('+') | curses.color_pair(4)
            
            tiles[13]['visible'] = ord("'") | curses.color_pair(6)
            tiles[13]['explored'] = ord("'") | curses.color_pair(4)

    def creat_draw(self, creat):
        x, y = creat.pos
        if x-self.xmap<0 or x-self.xmap>=self.maxmapx or y-self.ymap<0 or y-self.ymap>=self.maxmapy:
            return
        if not creat.world.flags[x][y] & FLAG_VISIBLE:
            return
        c = creat.char
        try:
            self.map.addch(y-self.ymap, x-self.xmap, c)
        except:
            pass

    def effect_draw(self, creat):
        x, y = creat.pos
        if x-self.xmap<0 or x-self.xmap>=self.maxmapx or y-self.ymap<0 or y-self.ymap>=self.maxmapy:
            return
        if creat.effect:
            c = creat.effect
            try:
                self.map.addch(y-self.ymap, x-self.xmap, c)
            except:
                pass

    def tile_draw(self, pos, world):
        x, y = pos
        flags = world.flags[x][y]
        tile = world.map[x][y]
        c = ' '
        if flags & FLAG_VISIBLE:
            c = tiles[tile]["visible"]
            try:
                s = tiles[tile]["shadow"]
                if world.flags[x-1][y-1] & FLAG_OPAQUE:
                    c = s
            except KeyError:
                pass
        elif flags & FLAG_EXPLORED:
            c = tiles[tile]["explored"]
            try:
                s = tiles[tile]["shadow"]
                if not world.flags[x-1][y-1] & FLAG_OPAQUE:
                    c |= curses.A_BOLD
            except KeyError:
                pass
        self.mapdraw(x, y, c)
            
    def light_draw(self, light):
        for pos in light.last:
            if not pos in light.lit:
                self.tile_draw(pos, light.world)
        for pos in light.lit:
            self.tile_draw(pos, light.world)
            
    def world_draw(self, world):
        self.map.clear()
        for x in range(world.width):
            for y in range(world.height):
                self.tile_draw((x, y), world)

    def mapdraw(self, x, y, c):
        if x-self.xmap<0 or x-self.xmap>=self.maxmapx or y-self.ymap<0 or y-self.ymap>=self.maxmapy:
            return
        try:
            self.map.addch(y-self.ymap, x-self.xmap, c)
        except:
            pass

    def select(self, x, y):
        if x-self.xmap<0 or x-self.xmap>=self.maxmapx or y-self.ymap<0 or y-self.ymap>=self.maxmapy:
            return
        if x-self.xmap == self.maxmapx-1 and y-self.ymap == self.maxmapy-1:
            return
        self.map.move(y-self.ymap, x-self.xmap)

    def shiftmap(self, x, y):
        self.xmap += x
        self.ymap += y
        
    def centermap(self, pos):
        x, y = pos
        sy, sx = self.map.getmaxyx()
        self.xmap = -sx/2+x
        self.ymap = -sy/2+y

    def refresh(self):
        self.msg.noutrefresh()
        self.inv.noutrefresh()
        self.map.noutrefresh()
        self.stat.noutrefresh()
        curses.doupdate()

    def get_command(self):
        cmd = None
        self.map.timeout(300)
        eff.draw(self)
        while not cmd:
            msg.draw(self)
            self.refresh()
            key = self.map.getkey()
            if key!="-1":
                try:
                    cmd = command_keys[key]
                except KeyError:
                    msg.write("What does %s mean?" % key)
            else:
                self.map.timeout(-1)
                eff.undraw(self)
                self.refresh()
        eff.clear()
        return cmd

class Zombie:
    def __init__(self, world, pos = None):
        self.world = world
        if pos:
            self.pos = pos
        else:
            x = random.randrange(1, world.width-2)
            y = random.randrange(1, world.height-2)
            while world.flags[x][y] & (FLAG_BLOCKED | FLAG_CREATURE):
                x += 1
                if x>=world.width-1:
                    x = 1
                    y += 1
            self.pos = (x, y)
        self.target = None
        self.norm_char = ord('Z') | curses.color_pair(2)
        self.trip_char = ord('z') | curses.color_pair(2)
        self.burn_char = ord('*') | curses.color_pair(3) | curses.A_BOLD
        self.char = self.norm_char
        world.creatures.append(self)
        self.world.flags[self.pos[0]][self.pos[1]] |= FLAG_CREATURE
        self.burn = 0
        self.dead = 0
        self.tripped = 0

    def trip(self):
        if not self.burn:
            self.char = self.trip_char
            self.tripped = 1
        
    def untrip(self):
        if not self.burn:
            self.char = self.norm_char
            self.tripped = 0

    def kill(self):
        msg.write("Thud!")
        self.dead = 1
        self.world.flags[self.pos[0]][self.pos[1]] &= ~FLAG_CREATURE
        if not self.world.map[self.pos[0]][self.pos[1]] in (MAP_CRATE, MAP_CORPSE, MAP_WALL, MAP_DOOR, MAP_ELEV):
            if random.randrange(100)>20:
                self.world.add_tile(self.pos, MAP_CORPSE)
            else:
                self.world.add_tile(self.pos, MAP_CORPSE+1)
        for i in range(len(self.world.creatures)):
            if self.world.creatures[i]==self:
                del self.world.creatures[i]
                break

    def bleed(self, amount=50):
        for dy in (-1, 0, 1):
            for dx in (-1, 0, 1):
                x = self.pos[0] + dx
                y = self.pos[1] + dy
                if self.world.map[x][y]==MAP_FLOOR:
                    if random.randrange(100)>110-amount:
                        self.world.add_tile((x,y), MAP_BLOOD)
                    elif random.randrange(100)>80-amount:
                        self.world.add_tile((x,y), MAP_BLOOD+1)
                elif self.world.map[x][y]==MAP_WALL:
                    if random.randrange(100)>130-amount:
                        self.world.add_tile((x,y), MAP_BLOOD+2)

    def wound(self):
        if random.randrange(100)>60:
            self.kill()

    def metabolism(self):
        if self.burn:
            if random.randrange(100)>90:
                self.kill()
        # That's what I call a good metablism! :)

    def hit_nail(self):
            self.wound()

    def hit_molotov(self):
        self.burn = 1
        self.char = self.burn_char
            
    def hit_pipe(self):
        self.wound()

    def pushback(self):
        hx, hy = self.world.hero.pos
        x, y = self.pos
        dx, dy = 0, 0
        if hx < x:
            dx = 1
        elif hx > x:
            dx = -1
        if hy < y:
            dy = 1
        elif hy > y:
            dy = -1
        try:
            self.act_go(dx, dy)
            self.trip()
        except ActError:
            pass

    def hit_revolver(self):
        self.bleed(40)
        self.wound()

    def hit_shotgun(self):
        hx, hy = self.world.hero.pos
        x, y = self.pos
        dx, dy = 0, 0
        if hx < x:
            dx = 1
        elif hx > x:
            dx = -1
        if hy < y:
            dy = 1
        elif hy > y:
            dy = -1
        try:
            self.act_go(dx, dy)
        except ActError:
            pass
        self.bleed(70)
        self.wound()
        self.wound()

    def draw(self, disp):
        x, y= self.pos
        if self.world.flags[x][y] & FLAG_VISIBLE:
            if self.world.hero.target == self.pos:
                disp.mapdraw(x, y, self.char|curses.A_BOLD)
            else:
                disp.mapdraw(x, y, self.char)

    def act_go(self, dx, dy):
        if dx==0 and dy==0:
            return
        x, y = self.pos
        npos = (x+dx, y+dy)
        nx, ny = npos
        if nx<0 or nx>=self.world.width or ny<0 or ny>=self.world.height:
            raise ActError
        if self.world.flags[nx][ny] & FLAG_BLOCKED:
            raise ActError
        if self.world.flags[nx][ny] & FLAG_CREATURE:
            raise ActError
        self.world.flags[x][y] &= ~FLAG_CREATURE
        self.pos = npos
        self.world.flags[nx][ny] |= FLAG_CREATURE

    def act_bite(self):
        first = random.choice((0, 1))
        if first:
            self.world.hero.defend(self)
        if random.randrange(100)>10:
            if distance(self.world.hero.pos, self.pos)<=1 and not self.dead:
                msg.write("Chomp!")
                self.world.hero.hit_bite()
        if not first:
            self.world.hero.defend(self)

    def act(self):
        x, y = self.pos
        if self.burn:
            dx, dy = random.choice((-1, 0, 1)), random.choice((-1,0,1))
            try:
                self.act_go(dx, dy)
            except ActError:
                nx = self.pos[0] + dx
                ny = self.pos[1] + dy
                creat = self.world.find_creature((nx, ny))
                if creat:
                    if creat!=self.world.hero:
                        creat.hit_molotov()
                    else:
                        self.act_bite()
            if self.world.flags[x][y] & FLAG_VISIBLE:
                eff.add(self.pos, ord('*') | curses.A_BOLD)
        elif self.tripped:
            self.untrip()
        else:
            if self.pos == self.target:
                self.target = None
            if self.world.flags[x][y] & FLAG_VISIBLE:
                self.target = self.world.hero.pos
            if self.target:
                hx, hy = self.target
                if distance(self.world.hero.pos, self.pos)<=1:
                    self.act_bite()
                else:
                    dx = 0
                    dy = 0
                    if hx < x:
                        dx = -1
                    elif hx > x:
                        dx = 1
                    if hy < y:
                        dy = -1
                    elif hy > y:
                        dy = 1
                    try:
                        self.act_go(dx, dy)
                    except ActError:
                        try:
                            if random.choice((0, 1)):
                                self.act_go(dx, 0)
                            else:
                                self.act_go(0, dy)
                        except ActError:
                            pass
        
class Hero(Zombie):
    def __init__(self, world, disp, pos=None):
        Zombie.__init__(self, world, pos)
        self.disp = disp
        self.disp.centermap(self.pos)
        self.char_stand = ord('@') | curses.A_BOLD
        self.char_trip = ord('a') | curses.A_BOLD
        self.char = self.char_stand
        self.light = Light(world)
        self.light.cast(self.pos[0], self.pos[1])
        self.inventory = Inventory()
        self.inventory.add_item(1)
        self.inventory.add_item(6)
        self.inventory.add_item(12)
        self.inventory.draw(self.disp)
        self.stamina = 100
        self.wounds = 0
        self.dead = 0
        self.food = 0

    def draw_stats(self):
        self.disp.stat.clear()
        wounds = ''
        for i in range(self.wounds):
            wounds += '*'
        self.disp.stat.addstr(0, 0, "S:%3d W:%s" % (self.stamina, wounds), curses.color_pair(5) | curses.A_BOLD)


    def kill(self):
        self.dead = 1
        msg.write("Aaargh! They got me! Plant an orchid on my grave...")

    def wound(self):
        eff.add(self.pos, ord('*') | curses.color_pair(5) | curses.A_BOLD)
        if self.wounds<5:
            self.wounds+=1
        if self.stamina==0:
            self.kill()

    def defend(self, creat):
        slot = self.inventory.eqp[KIND_WEAPON]
        if slot==None or self.stamina<=0:
            return
        weapon = items[self.inventory.inv[slot]]
        try:
            accuracy = weapon["accuracy"]
        except KeyError:
            accuracy = 100
        if random.randrange(100)<=accuracy:
            self.inventory.emit_sound(slot)
            try:
                if items[self.inventory.inv[slot]]["noisy"]:
                    self.world.noise()
            except KeyError:
                pass
            try:
                creat.bleed(weapon["blood"])
            except KeyError:
                pass
            creat.hit_pipe()
            if weapon['name']=='blowtorch':
                creat.hit_molotov()
            elif weapon['name']=='chainsaw':
                creat.kill()
            eff.add(creat.pos, ord('*') | curses.color_pair(5) | curses.A_BOLD )
            try:
                if items[self.inventory.inv[slot]]["push"]:
                    creat.pushback()
            except KeyError:
                pass
        self.inventory.useup_item(slot)
        if random.randrange(100)<15:
            msg.write("Broken.")
            self.inventory.del_item(slot)
        self.inventory.draw(self.disp)

    def metabolism(self):
        if self.food>0:
            amount = min(4, self.food)
            self.food -= amount
            self.stamina += amount
        self.stamina -= self.wounds;
        if self.stamina<0:
            self.stamina = 0
        if self.stamina>100:
            self.stamina = 100

    def hit_bite(self):
        if random.randrange(100)>10:
            msg.write("Ouch!")
            self.bleed(20)
            self.wound()

    def act_shoot(self):
        if not self.target:
            msg.write("Nobody here.")
            raise ActError
        gun_slot = self.inventory.eqp[KIND_GUN]
        if gun_slot==None:
            msg.write("Click!")
            raise ActError
        gun = items[self.inventory.inv[gun_slot]]
        creat = self.world.find_creature(self.target)
        if creat:
            try:
                accuracy = gun["accuracy"]
            except KeyError:
                accuracy = 100
            self.inventory.emit_sound(gun_slot)
            try:
                if items[self.inventory.inv[gun_slot]]["noisy"]:
                    self.world.noise()
            except KeyError:
                pass
            if random.randrange(100)<=accuracy:
                try:
                    creat.bleed(gun["blood"])
                except KeyError:
                    pass
                if gun["name"]=="nail gun":
                    creat.hit_nail()
                elif gun["name"]=="revolver":
                    creat.hit_revolver()
                elif gun["name"]=="shotgun":
                    creat.hit_revolver()
                    creat.pushback()
                elif gun["name"]=="molotov":
                    creat.hit_molotov()
                eff.add(creat.pos, ord('*') | curses.color_pair(5) | curses.A_BOLD )
            else:
                msg.write("Spak!")
                eff.add(creat.pos, ord('+') | curses.color_pair(3) | curses.A_BOLD )
            self.inventory.useup_item(gun_slot)
            self.inventory.draw(self.disp)
        else:
            msg.write("Sneaky bastard!")
            raise ActError

    def act_use(self, slot):
        if self.inventory.inv[slot]==0:
            msg.write("It's not there.")
            raise ActError
        item = items[self.inventory.inv[slot]]
        kind = item["kind"]
        if kind in (KIND_WEAPON, KIND_GUN):
            if self.inventory.eqp[kind]==slot:
                self.inventory.eqp[kind] = None
            else:
                self.inventory.eqp[kind] = slot
            self.inventory.draw(self.disp)
        elif kind == KIND_FOOD:
            self.act_apply(slot)
        else:
            msg.write("Useless.")
            raise ActError

    def act_trash(self, slot):
        if self.inventory.inv[slot]==0:
            msg.write("Gone already.")
            raise ActError
        self.inventory.del_item(slot)
        msg.write("Useless crap!")
        self.inventory.draw(self.disp)

    def act_loot(self):
        x, y = self.pos
        if not self.world.flags[x][y] & FLAG_ITEM:
            msg.write("Nothing here.")
            raise ActError
        item = self.inventory.add_random()
        if self.world.map[x][y] == MAP_CRATE:
            msg.write("Crash!")
        elif self.world.map[x][y] == MAP_CORPSE:
            msg.write("Rest in peace.")
        msg.write("This seems useful.")
        self.inventory.draw(self.disp)
        self.world.change(self.pos)

    def act_go(self, dx, dy):
        x, y = self.pos
        npos = (x+dx, y+dy)
        nx, ny = npos
        if nx<0 or nx>self.world.width or ny<0 or ny>self.world.height:
            raise ActError
        if self.world.flags[nx][ny] & FLAG_BLOCKED:
            msg.write("Bonk!")
            raise ActError
        if self.world.flags[nx][ny] & FLAG_CREATURE:
            creat = self.world.find_creature((nx, ny))
            if not creat:
                self.world.flags[nx][ny] &= ~FLAG_CREATURE
            else:
                msg.write("Pardon me.")
                creat.act_go(dx, dy)
        self.world.flags[x][y] &= ~FLAG_CREATURE
        self.pos = npos
        self.world.flags[nx][ny] |= FLAG_CREATURE

    def act_apply(self, slot):
        food = items[self.inventory.inv[slot]]
        try:
            self.wounds -= food["heal"]
            if self.wounds<0:
                self.wounds = 0
        except KeyError:
            pass
        try:
            self.food += food["food"]
            if self.food>200:
                self.food = 200
        except KeyError:
            pass
        self.inventory.emit_sound(slot)
        try:
            if items[self.inventory.inv[slot]]["noisy"]:
                self.world.noise()
        except KeyError:
            pass
        self.inventory.useup_item(slot)
        self.inventory.draw(self.disp)

    def act_trip(self):
        pass

    def act_run(self, dx, dy):
        self.act_go(dx, dy)
        if self.stamina>0:
            try:
                self.act_go(dx, dy)
                self.stamina -= 1
            except ActError:
                self.act_trip()
        else:
            msg.write('Pant, pant...')

    def act_operate(self):
        x, y = self.pos;
        if self.world.map[x][y] == MAP_ELEV:
            self.world.new_level()
            self.disp.world_draw(self.world)

    def act(self):
        x,y = self.pos
        running = 0
        done = 0
        self.target = None
        for creat in self.world.creatures:
            if self!=creat and self.world.flags[creat.pos[0]][creat.pos[1]] & FLAG_VISIBLE:
                if not self.target or distance(self.pos, creat.pos) < distance(self.pos, self.target):
                    self.target = creat.pos
        self.disp.light_draw(self.light)
        for creat in self.world.creatures:
            self.disp.creat_draw(creat)
        self.draw_stats()
        while not done:
            done = 1
            cmd, param = self.disp.get_command()
            try:
                if cmd == 'quit':
                    sys.exit()
                elif cmd == 'refresh':
                    done = 0
                elif cmd == 'wait':
                    msg.write("Yawn...")
                elif cmd == 'loot':
                    self.act_loot()
                elif cmd == 'use':
                    self.act_use(param)
                elif cmd == 'shoot':
                    self.act_shoot()
                elif cmd == 'trash':
                    self.act_trash(param)
                elif cmd == 'operate':
                    self.act_operate()
                elif cmd == 'run' and param==None:
                    done = 0
                    running = 1
                    msg.write("Where to run?")
                elif cmd=='go':
                  if running:
                    running = 0
                    self.act_run(param[0], param[1])
                  else:
                      self.act_go(param[0], param[1])
            except ActError:
                done = 0
            x,y = self.pos
            maxy, maxx = self.disp.map.getmaxyx()
            if x-self.disp.xmap<margin:
                self.disp.shiftmap(-margin, 0)
                self.disp.world_draw(self.world)
            elif y-self.disp.ymap<margin:
                self.disp.shiftmap(0, -margin)
                self.disp.world_draw(self.world)
            elif x-self.disp.xmap>self.disp.maxmapx-margin:
                self.disp.shiftmap(margin, 0)
                self.disp.world_draw(self.world)
            elif y-self.disp.ymap>self.disp.maxmapy-margin:
                self.disp.shiftmap(0, margin)
                self.disp.world_draw(self.world)
            self.light.cast(x, y)
        msg.clear()

class Inventory:
    def __init__(self):
        self.max_inv = 21
        self.inv = []
        self.charges = []
        self.eqp = [0, 1, None]
        for i in range(self.max_inv):
            self.inv.append(0)
            self.charges.append(0)

    def draw(self, disp):
        disp.inv.clear()
        for i in range(self.max_inv):
            if i in self.eqp:
                color = curses.A_BOLD | curses.color_pair(2)
            else:
                color = curses.A_NORMAL | curses.color_pair(2)
            if self.charges[i]>0:
                disp.inv.addstr("%s %s %d\n" % (curses.unctrl(ord('a')+i), items[self.inv[i]]['name'], self.charges[i]), color)
            else:
                disp.inv.addstr("%s %s\n" % (curses.unctrl(ord('a')+i), items[self.inv[i]]['name']), color)

    def add_item(self, item):
        for i in range(self.max_inv):
            if self.inv[i]==0:
                break
        if self.inv[i]:
            msg.write("No more room.")
            raise ActError
        self.inv[i] = item
        try:
            self.charges[i] = items[self.inv[i]]['charges']
        except KeyError:
            pass
    
    def del_item(self, item):
        self.inv[item] = 0
        self.charges[item] = 0
        for i in range(len(self.eqp)):
            if self.eqp[i]==item:
                self.eqp[i] = None

    def useup_item(self, slot):
        if self.charges[slot]>0:
            self.charges[slot] -= 1
            if self.charges[slot]<=0:
                msg.write("Empty.")
                self.del_item(slot)
        elif items[self.inv[slot]]["kind"]!=KIND_WEAPON:
            self.del_item(slot)
            
    def add_random(self):
        total = 0
        spot = random.randrange(item_maxprob)
        item = 0
        total += items[item]['prob']
        while total<=spot:
            item+=1
            total += items[item]['prob']
        self.add_item(item)
        return item

    def emit_sound(self, slot):
        try:
            msg.write(items[self.inv[slot]]["sound"])
        except KeyError:
            pass
                

    
def main(stdscr):
    maxy, maxx = stdscr.getmaxyx()
    if maxy<24 or maxx<80:
        raise GameError, "Screen too small, you need at last 80x24 characters."
    disp = Display(stdscr)
    world = World()
    world.hero = Hero(world, disp)
    disp.world_draw(world)
    while not world.hero.dead:
        for creat in world.creatures:
            creat.metabolism()
            creat.act()

msg = Mesg()
eff = Effect()
curses.wrapper(main)
msg.output()
