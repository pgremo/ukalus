package ukalus.items

import org.apache.commons.math3.random.MersenneTwister
import org.apache.commons.math3.random.RandomAdaptor
import ukalus.util.MarkovChain
import java.util.*
import java.util.function.Function

/**
 * @author pmgremo
 */
class ArtDescriptionResource : ListResourceBundle() {

    private val random = RandomAdaptor(MersenneTwister())

    private val rules = MarkovChain<Function<Any, String>>()

    private val methodWords = arrayOf("Channels", "Doors", "Forms", "Gates", "Images", "Means", "Methods", "Paths", "Rings", "Rules", "Stances", "Stems", "Styles", "Ways")

    private val numberNames = arrayOf("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve")

    private val nounWords = arrayOf("Boar", "Bear", "Tiger", "Phoenix", "Dragon", "Cauldron", "Tortoise", "Viper", "Toad", "Lizard", "Scorpion", "Centipede", "Beast", "Demon", "Fiend", "Ghoul", "Ghost", "Reaver", "Shadow", "Wraith", "Abyss", "Empyrean", "Pit", "Rift", "Temple", "Fortress", "Tower", "Soul", "Spirit", "Mind", "Will", "Chaos", "Death", "Destiny", "Doom", "Entropy", "Gale", "Glyph", "Havoc", "Mercy", "Meteor", "Order", "Pain", "Rule", "Rune", "Skull", "Stone", "Storm", "Warp")

    private val adjectiveWords = arrayOf("Hungering", "White", "Red", "Yellow", "Green", "Blue", "Purple", "Black", "Raising", "Falling", "Burning", "Freezing", "Crushing", "Splitting", "Pounding", "Drilling", "Crossing", "Shining", "Glowing", "Spinning", "Thundering", "Lightning", "Empty", "Dire", "Dread", "Greater", "Lesser", "Swift", "Sublime", "Linked", "Soft", "Hard", "Grim", "Hidden", "Ultimate", "Supreme", "Divine", "Bloodied", "Bone", "Poisonous", "Flaming", "Flowing", "Earthen", "Wooden", "Metallic", "Jade", "Iron", "Golden", "Silvery", "Diamond", "Ruby", "Sapphire", "Pearl", "Emerald")

    private val methods = RandomWord(random, methodWords)

    private val noun1 = RandomWord(random, nounWords)

    private val noun2 = RandomWord(random, nounWords)

    private val adjectives1 = RandomWord(random, adjectiveWords)

    private val adjectives2 = RandomWord(random, adjectiveWords)

    private val adjectives3 = RandomWord(random, adjectiveWords)

    private val types = RandomWord(random, arrayOf("Claw", "Fist", "Hand", "Palm", "Turns", "Breaks", "Grabs", "Strike", "Spin", "Kick", "Forms", "Trace", "Foot", "Elbow", "Game"))

    private val names = RandomName(random = random, fileName = "/ukalus/wordlists/egyptian.txt", minSyllables = 2, maxSyllables = 3)

    private val numbers = RandomNumber(random = random, min = 2, max = 108, names = numberNames)

    private val possessive = RandomWord(random, arrayOf("'s"))

    private val of = RandomWord(random, arrayOf("of"))

    private val the = RandomWord(random, arrayOf("the"))

    init {
        rules.add(null, names)
        rules.add(null, adjectives1)
        rules.add(null, noun1)
        rules.add(null, types)
        rules.add(null, types)
        rules.add(null, types)

        rules.add(names, possessive)
        rules.add(names, possessive)
        rules.add(names, adjectives1)
        rules.add(names, adjectives1)
        rules.add(names, noun1)
        rules.add(names, types)
        rules.add(names, types)
        rules.add(names, types)
        rules.add(names, types)

        rules.add(possessive, adjectives1)
        rules.add(possessive, adjectives1)
        rules.add(possessive, noun1)
        rules.add(possessive, types)
        rules.add(possessive, types)

        rules.add(adjectives1, adjectives2)
        rules.add(adjectives1, noun1)
        rules.add(adjectives1, types)
        rules.add(adjectives1, types)

        rules.add(adjectives2, noun1)
        rules.add(adjectives2, types)
        rules.add(adjectives2, types)

        rules.add(noun1, types)

        rules.add(types, of)
        rules.add(types, null)

        rules.add(of, the)
        rules.add(of, numbers)

        rules.add(numbers, methods)

        rules.add(methods, null)

        rules.add(the, adjectives3)

        rules.add(adjectives3, noun2)

        rules.add(noun2, null)
    }

    private fun generateName() = generateSequence { rules.randomWalk(random).map { it.apply(1) }.distinct() }
            .filter { it.count() > 1 }
            .first()
            .joinToString(" ")
            .replace(" 's".toRegex(), "'s")
            .replace("s's".toRegex(), "s'")

    override fun getContents() = generateSequence { generateName() }
            .distinct()
            .take(10)
            .toList()
            .mapIndexed { index, current -> arrayOf<Any>("art.description.$index", current) }
            .toTypedArray()
}