package ru.skittens.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Incident(
    val id: Int,
    val incidentPhotos: List<ParkPhoto>,
    val employeeXIncidents: List<User>,
    val incidentStatusXIncidents: List<Status>,
    val latitude: Double,
    val longitude: Double,
    val comment: String
)