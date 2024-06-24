package ru.skittens.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import ru.skittens.domain.repository.ParkRepository

class GetSelectedRouteUseCase(private val parkRepository: ParkRepository) {
    val selectedRouteId = MutableStateFlow(-1)
    val selectedRoute = combine(parkRepository.routesFlow, selectedRouteId){ routes, selectedId ->
        routes.data?.find { it.id == selectedId }
    }

    operator fun invoke(newId: Int) = selectedRouteId.update { newId }
}