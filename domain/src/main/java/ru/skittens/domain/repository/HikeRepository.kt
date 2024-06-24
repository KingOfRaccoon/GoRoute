package ru.skittens.domain.repository

import kotlinx.coroutines.flow.StateFlow
import ru.kingofraccoons.domain.util.Resource
import ru.skittens.domain.entity.Hike
import ru.skittens.domain.entity.HikeGroupRequest

interface HikeRepository {
    val groupsUserFlow: StateFlow<Resource<List<Hike>>>

    suspend fun loadGroup(idUser: Int, token: String)
    suspend fun addHikeGroup(hikeGroupRequest: HikeGroupRequest, token: String): Resource<String>
}