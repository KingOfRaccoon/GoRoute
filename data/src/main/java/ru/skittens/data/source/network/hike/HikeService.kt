package ru.skittens.data.source.network.hike

import ru.kingofraccoons.domain.util.Resource
import ru.skittens.data.util.Postman
import ru.skittens.domain.entity.Hike
import ru.skittens.domain.entity.HikeGroupRequest

class HikeService(private val postman: Postman) {
    private val baseUrl = "http://213.171.10.242/api/v1"
    private val getGroupsUserTag = "/userGroupsById"
    private val addHikeGroupTag = "/createHikeGroup"

    suspend fun getGroupsUser(idUser: Int, token: String): Resource<List<Hike>> {
        return postman.get(baseUrl, getGroupsUserTag, token = token, arguments = mapOf("id" to idUser))
    }

    suspend fun addHikeGroup(hikeGroupRequest: HikeGroupRequest, token: String): Resource<String>{
        return postman.post<String>(baseUrl, addHikeGroupTag, hikeGroupRequest, token).also {
            println("addHikeGroup: $it")
        }
    }
}