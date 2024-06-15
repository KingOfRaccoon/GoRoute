package ru.skittens.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Park(
    val id: String,
    val avgTouristsPY: String? = null,
    val borderColor: String,
    val borderCoords: List<String> = listOf(),
    val color: String,
    val description: String? = null,
    val name: String,
    val parkContacts: String? = null,
    val parkPhotos: List<ParkPhoto> = listOf(),
    val points: String,
    val siteUrl: String? = null,
    val title: String? = null,
    val workingTime: String? = null
) {
    fun getPointsList(): List<List<Double>> {
        val regex = "\\[([-\\d.]+),([-\\d.]+)]*".toRegex()
        return regex.findAll(points).map { listOf(it.groupValues[1].toDouble(), it.groupValues[2].toDouble()) }.toList()
    }
}