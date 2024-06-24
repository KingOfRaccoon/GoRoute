package ru.skittens.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.kingofraccoons.domain.util.Resource
import ru.skittens.data.source.network.hike.HikeService
import ru.skittens.domain.entity.Hike
import ru.skittens.domain.entity.HikeGroupRequest
import ru.skittens.domain.repository.HikeRepository

class HikeRepositoryImpl(private val hikeService: HikeService): HikeRepository {
    private val _groupsUserFlow = MutableStateFlow<Resource<List<Hike>>>(Resource.Loading())
    override val groupsUserFlow: StateFlow<Resource<List<Hike>>> = _groupsUserFlow.asStateFlow()

    override suspend fun loadGroup(idUser: Int, token: String) {
        _groupsUserFlow.update {
            hikeService.getGroupsUser(idUser, token)
        }
    }

    override suspend fun addHikeGroup(hikeGroupRequest: HikeGroupRequest, token: String): Resource<String> {
        return hikeService.addHikeGroup(hikeGroupRequest, token)
    }
}