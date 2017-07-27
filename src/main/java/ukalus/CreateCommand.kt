package ukalus

import ukalus.level.dungeon.recursive.HomeGenerator
import ukalus.level.dungeon.recursive.IsTileType
import ukalus.persistence.Persistence
import ukalus.persistence.PersistenceException

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class CreateCommand {

    /**
     * DOCUMENT ME!

     * @param client
     * *          DOCUMENT ME!
     */
    fun execute(name: String): Any {
        try {
            Persistence.create(name)
        } catch (e: PersistenceException) {
            e.printStackTrace()
        }

        val creature = Creature()
        creature.name = name

        val generator = HomeGenerator()
        val level = generator.generate("0")
        val predicate = IsTileType(Floor::class.java)

        val tile = level.getRandom(predicate)!!
        val floor = tile.tileType as Floor
        floor.creature = creature
        creature.coordinate = tile.location
        creature.level = level
        return creature
    }
}