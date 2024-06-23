package ru.skittens.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Hike(
    val endTime: String,
    val hikeInvites: List<HikeInvite>,
    val hikeRequests: List<String>,
    val id: Int,
    val isPrivate: Boolean,
    val membersCount: Int,
    val password: String,
    val startTime: String,
    val usersInHikeGroups: List<UsersInHikeGroup>
)