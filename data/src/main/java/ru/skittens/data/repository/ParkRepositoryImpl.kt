package ru.skittens.data.repository

import kotlinx.coroutines.flow.*
import ru.kingofraccoons.domain.util.Resource
import ru.skittens.data.source.network.parks.ParkService
import ru.skittens.domain.entity.*
import ru.skittens.domain.repository.ParkRepository

class ParkRepositoryImpl(private val parkService: ParkService): ParkRepository {
    private val _parksFlow = MutableStateFlow<Resource<List<Park>>>(Resource.Loading())
    override val parksFlow: StateFlow<Resource<List<Park>>> = _parksFlow.asStateFlow()
    
    private val _areasFlow = MutableStateFlow<Resource<List<Area>>>(Resource.Loading())
    override val areasFlow: StateFlow<Resource<List<Area>>> = _areasFlow.asStateFlow()
    
    private val _routesFlow = MutableStateFlow<Resource<List<Route>>>(Resource.Loading())
    override val routesFlow: StateFlow<Resource<List<Route>>> = _routesFlow.asStateFlow()

    private val _typesFlow = MutableStateFlow<Resource<List<Type>>>(Resource.Loading())
    override val typesFlow = _typesFlow.asStateFlow()

    private val _levelsFlow = MutableStateFlow<Resource<List<Level>>>(Resource.Loading())
    override val levelsFlow = _levelsFlow.asStateFlow()

    override suspend fun getParks(token: String) {
        _parksFlow.update {
            parkService.getParks(token)
        }
    }

    override suspend fun getAreas(token: String) {
        _areasFlow.update {
            parkService.getAreas(token)
        }
    }

    override suspend fun getRoutes(token: String) {
        _routesFlow.update {
            parkService.getRoutes(token)
        }
    }

    override suspend fun getLevelIncident(token: String) {
        _levelsFlow.update {
            parkService.getLevelIncident(token)
        }
    }

    override suspend fun getTypeIncident(token: String) {
        _typesFlow.update {
            parkService.getTypeIncident(token)
        }
    }
}