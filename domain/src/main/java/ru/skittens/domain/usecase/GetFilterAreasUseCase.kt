package ru.skittens.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import ru.kingofraccoons.domain.util.Resource
import ru.skittens.domain.repository.ParkRepository

class GetFilterAreasUseCase(parkRepository: ParkRepository) {
    val filtersFlow = MutableStateFlow(listOf<String>())
    val filteredAreasFlow = combine(parkRepository.areasFlow, filtersFlow) { areas, filters ->
        when (areas) {
            is Resource.Error -> Resource.Error(areas.message)
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> Resource.Success(areas.data.filter { it.id !in filters })
        }
    }

    operator fun invoke(newFilter: String) =
        filtersFlow.update { if (newFilter in it) it.minus(newFilter) else it.plus(newFilter) }

    fun addListFilter(filters: List<String>) =
        filtersFlow.update { if (filters.all { filter -> filter in it }) it - filters.toSet() else (it + filters).distinct() }
}