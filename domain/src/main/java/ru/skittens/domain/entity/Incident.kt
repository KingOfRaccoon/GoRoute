package ru.skittens.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Incident(
    val comment: String,
    val date: String,
    val employeesCross: List<UserIncident>,
    val id: Int,
    val incidentPhotos: List<ParkPhoto>,
    val incidentStatus: List<IncidentStatusItem>,
    val incidentType: IncidentType,
    val latitude: Double,
    val longitude: Double,
    val threatDegree: ThreatDegree,
    val user: List<UserIncident>
)