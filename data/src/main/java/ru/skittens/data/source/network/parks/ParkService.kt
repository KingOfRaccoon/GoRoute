package ru.skittens.data.source.network.parks

import ru.kingofraccoons.domain.util.Resource
import ru.skittens.data.util.Postman
import ru.skittens.domain.entity.*

class ParkService(private val postman: Postman){
    private val baseUrl = "http://213.171.10.242/api/v1"
    private val parksTag = "/parks"
    private val routesTag = "/routes"
    private val areasTag = "/areas"
    private val levelTag = "/threatDegrees"
    private val typeTag = "/incidentTypes"
    
    suspend fun getParks(token: String): Resource<List<Park>> {
        return postman.get(baseUrl, parksTag, token = token)
    }
    
    suspend fun getRoutes(token: String): Resource<List<Route>> {
        return postman.get(baseUrl, routesTag, token = token)
    }
    
    suspend fun getAreas(token: String): Resource<List<Area>> {
        return postman.get(baseUrl, areasTag, token = token)
    }

    suspend fun getLevelIncident(token: String): Resource<List<Level>> {
        return postman.get(baseUrl, levelTag, token = token)
    }

    suspend fun getTypeIncident(token: String): Resource<List<Type>> {
        return postman.get(baseUrl, typeTag, token = token)
    }
}