package ru.skittens.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class HikeGroupRequest(
    val dateStart: String,
    val dateEnd: String,
    val createdId: Int,
    val routeId: Int,
    val isPrivate: Boolean = false,
    val password: String = "123654",
    val membersCount: Int = 0
)