package ironfist.namegenerator;

import ironfist.util.ArraySet;
import ironfist.util.MarkovChain;
import ironfist.util.MersenneTwister;
import ironfist.util.Syllableizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListResourceBundle;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author pmgremo
 */
public class ArtNameResource extends ListResourceBundle {

  private static final String PREFIX = "art.description.";

  private static final String FILE_NAME = "/wordlists/tepa.txt";

  private static final int MAX_SYLLABLES = 3;

  private static final int MAX_LABELS = 10;

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

  private Factory names = new RandomNameFactory(random, FILE_NAME,
    MAX_SYLLABLES);

  private Factory numbers = new RandomNumberFactory(random, 2, 108, numberNames);

  private Factory[] factories = new Factory[]{
      names,
      adjectives,
      adjectives,
      nouns,
      numbers,
      methods,
      adjectives,
      nouns};

  private Object[][][] rules = {
      {{new Integer(15), " {0}"}, {new Integer(15), " {0}''''s"}},
      {{new Integer(50), " {1}"}},
      {{new Integer(10), " {2}"}},
      {{new Integer(50), " {3}"}},
      {{new Integer(100), " '{0}'"}},
      {{new Integer(25), " of {4} {5}"}, {new Integer(25), " of the {6} {7}"}},};

  private String generateName() {
    Set words = new ArraySet();

    for (int index = 0; index < factories.length; index++) {
      boolean duplicate = false;
      while (!duplicate) {
        duplicate = words.add(factories[index].generate(null));
      }
    }

    String pattern = " '{0}'";

    while (pattern.equals(" '{0}'")) {
      pattern = "";
      for (int i = 0; i < rules.length; i++) {
        boolean found = false;
        for (int j = 0; j < rules[i].length && !found; j++) {
          found = random.nextInt(100) < ((Integer) rules[i][j][0]).intValue();
          if (found) {
            pattern += (String) rules[i][j][1];
          }
        }
      }
    }

    return MessageFormat.format(pattern.trim(), words.toArray());
  }

  protected Object[][] getContents() {
    Map contents = new HashMap();
    Collection values = contents.values();

    while (contents.size() < MAX_LABELS) {
      String name = generateName();

      if (!values.contains(name)) {
        contents.put(PREFIX + contents.size(), name);
      }
    }

    Object[][] result = new Object[contents.size()][2];
    Iterator iterator = contents.entrySet()
      .iterator();

    for (int index = 0; iterator.hasNext(); index++) {
      Map.Entry current = (Map.Entry) iterator.next();
      result[index][0] = current.getKey();
      result[index][1] = current.getValue();
    }

    return result;
  }

  public final static void main(String[] args) throws Exception {
    Random random = new MersenneTwister();
    String[] types = new String[]{"Fist", "Palm", "Hand", "Claw"};
    ResourceBundle bundle = ResourceBundle.getBundle(ArtNameResource.class.getName());
    Enumeration keys = bundle.getKeys();

    while (keys.hasMoreElements()) {
      String pattern = bundle.getString((String) keys.nextElement());
      String type = types[random.nextInt(types.length)];
      System.out.println(MessageFormat.format(pattern, new Object[]{type}));
    }
  }

  private interface Factory {

    Object generate(Object argument);
  }

  private class RandomWordFactory implements Factory {

    private Random random;

    private String[] items;

    public RandomWordFactory(Random random, String[] items) {
      this.random = random;
      this.items = items;
    }

    public Object generate(Object argument) {
      return items[random.nextInt(items.length)];
    }
  }

  private class RandomNumberFactory implements Factory {

    private String[] names;

    private Random random;

    private int min;

    private int max;

    public RandomNumberFactory(Random random, int min, int max, String[] names) {
      this.random = random;
      this.min = min;
      this.max = max;
      this.names = names;
    }

    public Object generate(Object argument) {
      int value = random.nextInt(max - min) + min;
      if (names != null && value < names.length) {
        return names[value];
      }
      return String.valueOf(value);
    }
  }

  private class RandomNameFactory implements Factory {

    private Random random;

    private MarkovChain chains;

    private int maxSyllables;

    public RandomNameFactory(Random random, String fileName, int maxSyllables) {
      this.random = random;
      chains = new MarkovChain();

      try {
        Reader reader = new BufferedReader(new InputStreamReader(
          getClass().getResourceAsStream(fileName)));
        StreamTokenizer tokenizer = new StreamTokenizer(reader);

        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
          if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
            String[] syllables = Syllableizer.split(tokenizer.sval.toLowerCase());
            String key = syllables[0];

            for (int i = 1; i < syllables.length; i++) {
              chains.add(key, syllables[i]);
              key = syllables[i];
            }
          }
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      this.maxSyllables = maxSyllables;
    }

    public Object generate(Object argument) {
      int syllableCount = random.nextInt(maxSyllables) + 1;
      StringBuffer result = new StringBuffer();
      Object key = chains.next(null, random.nextDouble());

      while (syllableCount-- > -1 && key != null) {
        result.append(key);
        key = chains.next(key, random.nextDouble());
      }

      return result.substring(0, 1)
        .toUpperCase() + result.substring(1);
    }
  }
}