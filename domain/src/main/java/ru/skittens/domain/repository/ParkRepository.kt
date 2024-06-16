package ru.skittens.domain.repository

import kotlinx.coroutines.flow.StateFlow
import ru.kingofraccoons.domain.util.Resource
import ru.skittens.domain.entity.*

interface ParkRepository {
    val parksFlow: StateFlow<Resource<List<Park>>>
    val areasFlow: StateFlow<Resource<List<Area>>>
    val routesFlow: StateFlow<Resource<List<Route>>>
    val typesFlow: StateFlow<Resource<List<Type>>>
    val levelsFlow: StateFlow<Resource<List<Level>>>
    
    suspend fun getParks(token: String)
    suspend fun getAreas(token: String)
    suspend fun getRoutes(token: String)
    suspend fun getTypeIncident(token: String)
    suspend fun getLevelIncident(token: String)
}