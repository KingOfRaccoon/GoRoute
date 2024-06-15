package ru.skittens.domain.repository

import kotlinx.coroutines.flow.StateFlow
import ru.kingofraccoons.domain.util.Resource
import ru.skittens.domain.entity.Area
import ru.skittens.domain.entity.Park
import ru.skittens.domain.entity.Route

interface ParkRepository {
    val parksFlow: StateFlow<Resource<List<Park>>>
    val areasFlow: StateFlow<Resource<List<Area>>>
    val routesFlow: StateFlow<Resource<List<Route>>>
    
    suspend fun getParks(token: String)
    suspend fun getAreas(token: String)
    suspend fun getRoutes(token: String)
}