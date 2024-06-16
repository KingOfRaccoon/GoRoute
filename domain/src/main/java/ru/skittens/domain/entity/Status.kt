package ru.skittens.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Status(
    val title: String,
    val description: String,
    val employeeId: Int,
    val incidentId: Int,
    val id: Int = 0
)
