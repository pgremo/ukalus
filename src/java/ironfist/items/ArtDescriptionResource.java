package ironfist.items;

import ironfist.util.MarkovChain;
import ironfist.util.MersenneTwister;
import static ironfist.util.Strings.join;

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
  private MarkovChain<Factory> rules = new MarkovChain<Factory>(random);

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
      "Ways"});

  private String[] nounWords = new String[]{
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
      "Warp"};

  private String[] adjectiveWords = new String[]{
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
      "Emerald"};

  private Factory noun1 = new RandomWordFactory(random, nounWords);

  private Factory noun2 = new RandomWordFactory(random, nounWords);

  private Factory adjectives1 = new RandomWordFactory(random, adjectiveWords);

  private Factory adjectives2 = new RandomWordFactory(random, adjectiveWords);

  private Factory adjectives3 = new RandomWordFactory(random, adjectiveWords);

  private Factory types = new RandomWordFactory(random, new String[]{
      "Claw",
      "Fist",
      "Hand",
      "Palm"});

  private Factory names = new RandomNameFactory(random, FILE_NAME,
    MIN_SYLLABLES, MAX_SYLLABLES);

  private Factory numbers = new RandomNumberFactory(random, 2, 108, numberNames);

  private Factory possesive = new RandomWordFactory(random, new String[]{"'s"});

  private Factory of = new RandomWordFactory(random, new String[]{"of"});

  private Factory the = new RandomWordFactory(random, new String[]{"the"});

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
      Iterator<Factory> iterator = rules.iterator();
      while (iterator.hasNext()) {
        Factory key = iterator.next();
        while (!parts.add(key.generate(new Integer(1)))) {
        }
      }
    } while (parts.size() == 1);

    return join(parts, " ").replaceAll(" 's", "'s")
      .replaceAll("s's", "s'");
  }

  protected Object[][] getContents() {
    Object[][] result = new Object[MAX_LABELS][];
    Set<Object> contents = new HashSet<Object>();
    while (contents.size() < MAX_LABELS) {
      String current = generateName();
      if (contents.add(current)) {
        int index = contents.size() - 1;
        result[index] = new Object[]{PREFIX + index, current};
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