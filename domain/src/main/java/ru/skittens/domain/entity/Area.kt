package ru.skittens.domain.entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Area(
    val cuts: String? = null,
    val defaultCoord: String,
    val defaultZoom: Int,
    val description: String,
    val geometry: String,
    val id: String,
    val image: String,
    val name: String,
    val pos: Int,
    val price: Double,
    val purposeOfVisit: String? = null,
    val subname: String,
    val ticketName: String? = null
) {

    fun getGeometryData(): GeometryData {
        return Json.decodeFromString(
            geometry
                .replace("\n", "")
                .replace("""\""", "").also { println("geometry: $it") }
        )
    }
}

@Serializable
data class GeometryData(
    val zoom: Int,
    val latitude: Double,
    val longitude: Double,
    val areas: List<CoordinatesData>
)

@Serializable
data class CoordinatesData(
    val name: String = "",
    val coordinates: List<List<List<Double>>>
)