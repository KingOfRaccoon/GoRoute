package ru.skittens.data.source.network.parks

import ru.kingofraccoons.domain.util.Resource
import ru.skittens.data.util.Postman
import ru.skittens.domain.entity.Area
import ru.skittens.domain.entity.Park
import ru.skittens.domain.entity.Route

class ParkService(private val postman: Postman){
    private val baseUrl = "http://213.171.10.242/api/v1"
    private val parksTag = "/parks"
    private val routesTag = "/routes"
    private val areasTag = "/areas"
    
    suspend fun getParks(token: String): Resource<List<Park>> {
        return postman.get(baseUrl, parksTag, token = token)
    }
    
    suspend fun getRoutes(token: String): Resource<List<Route>> {
        return postman.get(baseUrl, routesTag, token = token)
    }
    
    suspend fun getAreas(token: String): Resource<List<Area>> {
        return postman.get(baseUrl, areasTag, token = token)
    }
}