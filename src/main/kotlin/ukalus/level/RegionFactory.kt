package ukalus.level


interface RegionFactory<T> {

    fun create(): Region<T>

}