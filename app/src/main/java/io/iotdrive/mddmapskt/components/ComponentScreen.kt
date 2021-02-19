package io.iotdrive.mddmapskt.components

class ComponentScreen(val title: String?, val sections: List<Section> = emptyList())

data class MddAction(val type: String?, val url: String?, val payload: String?)
data class MddCoordinates(val lat: Double, val lon: Double)
data class MddSubtitle(val key: String, val value: String)

sealed class Section()
data class MddMapComponent(val title: String?, val items: List<MddCarouselComponentItem>): Section()
data class MddCarouselComponentItem(
    val title: String?,
    val subtitles: List<MddSubtitle>,
    val action: MddAction,
    val coordinates: MddCoordinates): Section()


