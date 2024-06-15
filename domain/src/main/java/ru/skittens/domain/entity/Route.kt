package ru.skittens.domain.entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Route(
    val additionalInfo: String? = null,
    val color: String? = null,
    val description: String = "",
    val id: Int = 0,
    val name: String = "",
    val onMap: Boolean = false,
    val paths: String = "",
    val title: String? = null,
    val areaId: String = "",
    val routeId: String = "",
    var length: Double = 0.0
) {

    fun getPathData(): List<PathData> {
        return Json.decodeFromString(
            paths
                .replace("\n", "")
                .replace("""\""", "")
        )
    }
}

@Serializable
data class PathData(
    val name: String,
    val color: String,
    val points: List<List<Double>>
)