package ironfist.items;

import ironfist.util.Closure;
import ironfist.util.Loop;
import ironfist.util.MarkovChain;
import ironfist.util.MersenneTwister;
import ironfist.util.StringJoin;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.ListResourceBundle;
import java.util.Random;
import java.util.Set;

/**
 * @author pmgremo
 */
public class ArtDescriptionResource extends ListResourceBundle {

  private static final String PREFIX = "art.description.";
  private static final String FILE_NAME = "/wordlists/egyptian.txt";
  private static final int MIN_SYLLABLES = 2;
  private static final int MAX_SYLLABLES = 3;
  private static final int MAX_LABELS = 10;

  private Random random = new MersenneTwister();
  private MarkovChain<Closure<Object, String>> rules = new MarkovChain<Closure<Object, String>>(
      random);

  private String[] methodWords = new String[] {
      "Channels",
      "Doors",
      "Forms",
      "Gates",
      "Images",
      "Means",
      "Methods",
      "Paths",
      "Rings",
      "Rules",
      "Stances",
      "Stems",
      "Styles",
      "Ways" };

  private String[] numberNames = new String[] {
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
      "Twelve" };

  private String[] nounWords = new String[] {
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
      "Abyss",
      "Empyrion",
      "Pit",
      "Rift",
      "Temple",
      "Fortress",
      "Tower",
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
      "Warp" };

  private String[] adjectiveWords = new String[] {
      "White",
      "Red",
      "Yellow",
      "Green",
      "Blue",
      "Purple",
      "Black",
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
      "Bloodied",
      "Bone",
      "Poisonous",
      "Flaming",
      "Flowing",
      "Earthen",
      "Wooden",
      "Metalic",
      "Jade",
      "Iron",
      "Golden",
      "Silvery",
      "Diamond",
      "Ruby",
      "Saphire",
      "Pearl",
      "Emerald" };

  private Closure<Object, String> methods = new RandomWord(random, methodWords);

  private Closure<Object, String> noun1 = new RandomWord(random, nounWords);

  private Closure<Object, String> noun2 = new RandomWord(random, nounWords);

  private Closure<Object, String> adjectives1 = new RandomWord(random,
      adjectiveWords);

  private Closure<Object, String> adjectives2 = new RandomWord(random,
      adjectiveWords);

  private Closure<Object, String> adjectives3 = new RandomWord(random,
      adjectiveWords);

  private Closure<Object, String> types = new RandomWord(random, new String[] {
      "Claw",
      "Fist",
      "Hand",
      "Palm" });

  private Closure<Object, String> names = new RandomName(random, FILE_NAME,
      MIN_SYLLABLES, MAX_SYLLABLES);

  private Closure<Object, String> numbers = new RandomNumber(random, 2, 108,
      numberNames);

  private Closure<Object, String> possesive = new RandomWord(random,
      new String[] { "'s" });

  private Closure<Object, String> of = new RandomWord(random,
      new String[] { "of" });

  private Closure<Object, String> the = new RandomWord(random,
      new String[] { "the" });

  {
    rules.add(null, names);
    rules.add(null, adjectives1);
    rules.add(null, noun1);
    rules.add(null, types);
    rules.add(null, types);
    rules.add(null, types);

    rules.add(names, possesive);
    rules.add(names, possesive);
    rules.add(names, adjectives1);
    rules.add(names, adjectives1);
    rules.add(names, noun1);
    rules.add(names, types);
    rules.add(names, types);
    rules.add(names, types);
    rules.add(names, types);

    rules.add(possesive, adjectives1);
    rules.add(possesive, adjectives1);
    rules.add(possesive, noun1);
    rules.add(possesive, types);
    rules.add(possesive, types);

    rules.add(adjectives1, adjectives2);
    rules.add(adjectives1, noun1);
    rules.add(adjectives1, types);
    rules.add(adjectives1, types);

    rules.add(adjectives2, noun1);
    rules.add(adjectives2, types);
    rules.add(adjectives2, types);

    rules.add(noun1, types);

    rules.add(types, of);
    rules.add(types, null);

    rules.add(of, the);
    rules.add(of, numbers);

    rules.add(numbers, methods);

    rules.add(methods, null);

    rules.add(the, adjectives3);

    rules.add(adjectives3, noun2);

    rules.add(noun2, null);
  }

  private String generateName() {
    Set<String> parts = new LinkedHashSet<String>();
    do {
      parts.clear();
      Iterator<Closure<Object, String>> iterator = rules.iterator();
      while (iterator.hasNext()) {
        Closure<Object, String> key = iterator.next();
        while (!parts.add(key.apply(new Integer(1)))) {
        }
      }
    } while (parts.size() == 1);

    StringBuffer result = new StringBuffer();
    new Loop<String>(parts).forEach(new StringJoin(" ", result));
    return result.toString()
      .replaceAll(" 's", "'s")
      .replaceAll("s's", "s'");
  }

  protected Object[][] getContents() {
    Object[][] result = new Object[MAX_LABELS][];
    Set<Object> contents = new HashSet<Object>();
    while (contents.size() < MAX_LABELS) {
      String current = generateName();
      if (contents.add(current)) {
        int index = contents.size() - 1;
        result[index] = new Object[] { PREFIX + index, current };
      }
    }
    return result;
  }

  public String toString() {
    StringBuffer result = new StringBuffer();
    Enumeration enumeration = getKeys();
    while (enumeration.hasMoreElements()) {
      String key = (String) enumeration.nextElement();
      if (result.length() > 1) {
        result.append("\n");
      }
      result.append(key)
        .append("=")
        .append(getObject(key));
    }
    return result.toString();
  }
}