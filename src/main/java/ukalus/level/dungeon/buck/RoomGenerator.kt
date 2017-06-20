package ukalus.level.dungeon.buck

import ukalus.level.Region
import ukalus.level.RegionFactory
import java.util.*

class RoomGenerator(val random: Random,
                    val minRoomHeight: Int,
                    val maxRoomHeight: Int,
                    val minRoomWidth: Int,
                    val maxRoomWidth: Int,
                    var startId: Int) : RegionFactory<Int> {

    override fun create(): Region<Int> {
        return Room(
                random,
                random.nextInt(maxRoomHeight - minRoomHeight) + minRoomHeight,
                random.nextInt(maxRoomWidth - minRoomWidth) + minRoomWidth,
                startId++
        )
    }
}
