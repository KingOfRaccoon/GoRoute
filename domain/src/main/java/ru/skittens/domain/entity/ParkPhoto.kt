package ru.skittens.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ParkPhoto(
    val id: Int,
    val url: String?
)