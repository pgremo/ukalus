package ironfist.next.items;

import ironfist.util.ArraySet;
import ironfist.util.MarkovChain;
import ironfist.util.MersenneTwister;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListResourceBundle;
import java.util.Random;
import java.util.Set;

/**
 * @author pmgremo
 */
public class ArtDescriptionResource extends ListResourceBundle {

  private static final String PREFIX = "art.description.";

  private static final String FILE_NAME = "/wordlists/tepa.txt";

  private static final int MAX_SYLLABLES = 3;

  private static final int MAX_LABELS = 10;

  private static final MarkovChain CHAIN = new MarkovChain();

  private Random random = new MersenneTwister();

  private String[] numberNames = new String[]{
      "One",
      "Two",
      "Three",
      "Four",
      "Five",
      "Six",
      "Seven",
      "Eight",
      "Nine",
      "Ten",
      "Eleven",
      "Twelve"};

  private Factory methods = new RandomWordFactory(random, new String[]{
      "Methods",
      "Ways",
      "Means",
      "Methods",
      "Forms",
      "Rules",
      "Stances",
      "Images",
      "Gates",
      "Stems",
      "Rings",
      "Channels",
      "Styles",
      "Doors"});

  private Factory nouns = new RandomWordFactory(random, new String[]{

      //Creatures
      "Tiger",
      "Pheonix",
      "Dragon",
      "Cauldron",
      "Tortoise",
      "Viper",
      "Toad",
      "Lizard",
      "Scorpion",
      "Centipede",
      "Beast",
      "Demon",
      "Fiend",
      "Ghoul",
      "Ghost",
      "Reaver",
      "Shadow",
      "Wraith",

      // Place
      "Abyss",
      "Empyrion",
      "Pit",
      "Rift",
      "Temple",
      "Fortress",
      "Tower",

      //	Thing
      "Soul",
      "Spirit",
      "Mind",
      "Will",
      "Chaos",
      "Death",
      "Destiny",
      "Doom",
      "Entropy",
      "Gale",
      "Glyph",
      "Havoc",
      "Mercy",
      "Meteor",
      "Order",
      "Pain",
      "Rule",
      "Rune",
      "Skull",
      "Stone",
      "Storm",
      "Warp"});

  private Factory adjectives = new RandomWordFactory(random, new String[]{

      // Colors
      "White",
      "Red",
      "Yellow",
      "Green",
      "Blue",
      "Purple",
      "Black",

      // Verbs
      "Raising",
      "Falling",
      "Burning",
      "Freezing",
      "Crushing",
      "Splitting",
      "Pounding",
      "Drilling",
      "Crossing",
      "Shining",
      "Glowing",
      "Spinning",

      // State?
      "Thundering",
      "Lightning",
      "Empty",
      "Dire",
      "Dread",
      "Greater",
      "Lesser",
      "Swift",
      "Sublime",
      "Linked",
      "Soft",
      "Hard",
      "Grim",
      "Hidden",
      "Ultimate",
      "Supreme",
      "Divine",

      // Material
      "Blood",
      "Bone",
      "Poison",
      "Fire",
      "Water",
      "Earth",
      "Wood",
      "Metal",
      "Jade",
      "Iron",
      "Gold",
      "Silver",
      "Diamond",
      "Ruby",
      "Saphire",
      "Pearl",
      "Emerald"});

  private Factory types = new RandomWordFactory(random, new String[]{
      "Claw",
      "Fist",
      "Hand",
      "Palm"});

  private Factory names = new RandomNameFactory(random, FILE_NAME,
    MAX_SYLLABLES);

  private Factory numbers = new RandomNumberFactory(random, 2, 108, numberNames);

  private Factory[] factories = new Factory[]{
      names,
      adjectives,
      adjectives,
      nouns,
      types,
      numbers,
      methods,
      adjectives,
      nouns};

  static {
    CHAIN.add(null, " {0}");
    CHAIN.add(null, " {0}");

    CHAIN.add(null, " {0}''s");
    CHAIN.add(null, " {0}''s");

    CHAIN.add(null, " {1}");
    CHAIN.add(null, " {1}");
    CHAIN.add(null, " {1}");
    CHAIN.add(null, " {1}");

    CHAIN.add(null, " {2}");

    CHAIN.add(null, " {3}");
    CHAIN.add(null, " {3}");
    CHAIN.add(null, " {3}");
    CHAIN.add(null, " {3}");

    CHAIN.add(null, " {4}");
    CHAIN.add(null, " {4}");
    CHAIN.add(null, " {4}");
    CHAIN.add(null, " {4}");

    CHAIN.add(" {0}", " {1}");
    CHAIN.add(" {0}", " {1}");
    CHAIN.add(" {0}", " {2}");
    CHAIN.add(" {0}", " {3}");
    CHAIN.add(" {0}", " {3}");
    CHAIN.add(" {0}", " {4}");
    CHAIN.add(" {0}", " {4}");
    CHAIN.add(" {0}", " {4}");
    CHAIN.add(" {0}", " {4}");

    CHAIN.add(" {0}''s", " {1}");
    CHAIN.add(" {0}''s", " {1}");
    CHAIN.add(" {0}''s", " {2}");
    CHAIN.add(" {0}''s", " {3}");
    CHAIN.add(" {0}''s", " {3}");
    CHAIN.add(" {0}''s", " {4}");
    CHAIN.add(" {0}''s", " {4}");
    CHAIN.add(" {0}''s", " {4}");
    CHAIN.add(" {0}''s", " {4}");

    CHAIN.add(" {1}", " {2}");
    CHAIN.add(" {1}", " {3}");
    CHAIN.add(" {1}", " {3}");
    CHAIN.add(" {1}", " {4}");
    CHAIN.add(" {1}", " {4}");
    CHAIN.add(" {1}", " {4}");
    CHAIN.add(" {1}", " {4}");

    CHAIN.add(" {2}", " {3}");
    CHAIN.add(" {2}", " {4}");
    CHAIN.add(" {2}", " {4}");

    CHAIN.add(" {3}", " {4}");

    CHAIN.add(" {4}", " of {5} {6}");
    CHAIN.add(" {4}", " of the {7} {8}");
    CHAIN.add(" {4}", null);
    CHAIN.add(" {4}", null);
  }

  private String generateName() {
    Set words = new ArraySet();
    for (int index = 0; index < factories.length; index++) {
      while (!words.add(factories[index].generate(new Integer(1)))) {
      }
    }

    StringBuffer pattern = new StringBuffer();
    do {
      pattern.setLength(0);
      Object key = CHAIN.next(null, random.nextDouble());
      while (key != null) {
        pattern.append(key);
        key = CHAIN.next(key, random.nextDouble());
      }
    } while (pattern.toString()
      .equals(" {4}"));
    
    return MessageFormat.format(pattern.toString()
      .trim(), words.toArray());
  }

  protected Object[][] getContents() {
    Set contents = new HashSet();

    while (contents.size() < MAX_LABELS) {
      contents.add(generateName());
    }

    Object[][] result = new Object[contents.size()][2];
    Iterator iterator = contents.iterator();

    for (int index = 0; iterator.hasNext(); index++) {
      Object current = iterator.next();
      result[index][0] = PREFIX + index;
      result[index][1] = current.toString();
    }

    return result;
  }
}