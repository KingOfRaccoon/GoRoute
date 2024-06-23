package ru.skittens.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class IncidentStatusItem(
    val date: String,
    val description: String,
    val id: Int,
    val title: String
)