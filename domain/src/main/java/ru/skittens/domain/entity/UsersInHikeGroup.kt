package ru.skittens.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class UsersInHikeGroup(
    val accountNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val authorities: List<Authority>,
    val credentialsNonExpired: Boolean,
    val email: String,
    val enabled: Boolean,
    val firstname: String,
    val hikeGroups: List<String>,
    val hikeInvites: List<String>,
    val hikeInvitesCreator: List<String>,
    val hikeRequests: List<String>,
    val hikeRequestsCreator: List<String>,
    val id: Int,
    val incidentStatuses: List<String>,
    val lastname: String,
    val role: String,
    val username: String
)