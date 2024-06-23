package ru.skittens.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import ru.kingofraccoons.domain.util.Resource
import ru.skittens.domain.repository.ParkRepository
import ru.skittens.domain.repository.UserRepository

class GetRoutesForSelectedAreaOrPark(private val userRepository: UserRepository, private val parkRepository: ParkRepository) {
    val selectedIdFlow = MutableStateFlow("")
    val routesFlow = combine(parkRepository.routesFlow, selectedIdFlow) { routes, id ->
        when(routes){
            is Resource.Error -> Resource.Error(routes.message)
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> Resource.Success(routes.data.filter { it.areaId == id || it.routeId == id })
        }
    }
    
    operator fun invoke(selectedId: String) {
        selectedIdFlow.update { selectedId }
    }
    
    suspend fun loadRoutes(){
        parkRepository.getRoutes(userRepository.getToken().orEmpty())
    }
}