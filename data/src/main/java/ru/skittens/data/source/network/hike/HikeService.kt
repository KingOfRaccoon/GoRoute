package ru.skittens.data.source.network.hike

import ru.kingofraccoons.domain.util.Resource
import ru.skittens.data.util.Postman
import ru.skittens.domain.entity.Hike

class HikeService(private val postman: Postman) {
    private val baseUrl = "http://213.171.10.242/api/v1"
    private val getGroupsUserTag = "/userGroupsById"

    suspend fun getGroupsUser(idUser: Int, token: String): Resource<List<Hike>> {
        return postman.get(baseUrl, getGroupsUserTag, token = token, arguments = mapOf("id" to idUser))
    }
}