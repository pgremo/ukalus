package ukalus

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
interface Client {

    /**
     * DOCUMENT ME!

     * @param level
     * *          DOCUMENT ME!
     */
    fun onLevelChange(level: Level)

    /**
     * DOCUMENT ME!

     * @param list
     * *          DOCUMENT ME!
     */
    fun onVisionChange(list: List<Tile>)
}