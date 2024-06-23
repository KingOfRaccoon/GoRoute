package ru.skittens.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import ru.kingofraccoons.domain.util.Resource
import ru.skittens.domain.entity.RouteLength
import ru.skittens.domain.repository.ParkRepository
import ru.skittens.domain.repository.UserRepository
import java.math.RoundingMode
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class GetRoutesForChooseUseCase(
    private val userRepository: UserRepository,
    private val parkRepository: ParkRepository
) {
    private val routes = parkRepository.routesFlow
    private val selectedId = MutableStateFlow<String?>(null)
    private val routeLengthMapFlow = MutableStateFlow(RouteLength())
    val seasonalityFlow = MutableStateFlow("")
    val typeRouteFlow = MutableStateFlow("")
    val conditionsFlow = MutableStateFlow("")
    val typeWayFlow = MutableStateFlow("")
    val difficultyFlow = MutableStateFlow("")

    val seasonalityListFlow = MutableStateFlow(List(3) { "Сезонность $it" })
    val typeRouteListFlow = MutableStateFlow(List(3) { "Тип маршрута $it" })
    val conditionsListFlow = MutableStateFlow(List(3) { "Условия посещения $it" })
    val typeWayListFlow = MutableStateFlow(List(3) { "Способ перемещения $it" })
    val difficultyListFlow = MutableStateFlow(List(3) { "Сложность $it" })

    val routesForChooseFlow =
        combine(routes, selectedId, routeLengthMapFlow) { routes, id, length ->
            if (id.isNullOrEmpty())
                when (routes) {
                    is Resource.Error -> Resource.Error(routes.message)
                    is Resource.Loading -> Resource.Loading()
                    is Resource.Success -> Resource.Success(routes.data
                        .filter { it.getPathData().isNotEmpty() }
                        .map { it.copy(length = length.map[it.id] ?: 0.0) }.onEach { route ->
                            val points = route.getPathData().map { it.points }.flatten()
                            if (points.isNotEmpty())
                                routeLengthMapFlow.update {
                                    it.copy(
                                        (it.map + (route.id to haversine(
                                            points.first()[1],
                                            points.first()[0],
                                            points.last()[1],
                                            points.last()[0]
                                        ))).toMutableMap()
                                    )
                                }
                        })
                }
            else
                when (routes) {
                    is Resource.Error -> Resource.Error(routes.message)
                    is Resource.Loading -> Resource.Loading()
                    is Resource.Success -> Resource.Success(routes.data.filter {
                        it.areaId == id && it.getPathData().isNotEmpty()
                    }
                        .map { it.copy(length = length.map[it.id] ?: 0.0) }.onEach { route ->
                            val points = route.getPathData().map { it.points }.flatten()
                            if (points.isNotEmpty())
                                routeLengthMapFlow.update {
                                    it.copy(
                                        (it.map + (route.id to haversine(
                                            points.first()[1],
                                            points.first()[0],
                                            points.last()[1],
                                            points.last()[0]
                                        ))).toMutableMap()
                                    )
                                }
                        })
                }
        }

    fun setSeasonality(newSeasonality: String) =
        seasonalityFlow.update { if (it != newSeasonality) newSeasonality else "" }

    fun setTypeRoute(newTypeRoute: String) =
        typeRouteFlow.update { if (it != newTypeRoute) newTypeRoute else "" }

    fun setConditions(newConditions: String) =
        conditionsFlow.update { if (it != newConditions) newConditions else "" }

    fun setTypeWay(newTypeWay: String) =
        typeWayFlow.update { if (it != newTypeWay) newTypeWay else "" }

    fun setDifficulty(newDifficulty: String) =
        difficultyFlow.update { if (it != newDifficulty) newDifficulty else "" }

    fun setId(newId: String?) {
        selectedId.update {
            newId
        }
    }

    suspend fun loadRoutes() = parkRepository.getRoutes(userRepository.getToken().orEmpty())

    fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371.0 // Радиус Земли в километрах

        // Преобразование градусов в радианы
        val lat1Rad = Math.toRadians(lat1)
        val lon1Rad = Math.toRadians(lon1)
        val lat2Rad = Math.toRadians(lat2)
        val lon2Rad = Math.toRadians(lon2)

        // Разница координат
        val dLat = lat2Rad - lat1Rad
        val dLon = lon2Rad - lon1Rad

        // Формула гаверсинуса
        val a = sin(dLat / 2).pow(2) + cos(lat1Rad) * cos(lat2Rad) * sin(dLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        // Расстояние
        return (R * c).toBigDecimal().setScale(1, RoundingMode.DOWN).toDouble()
    }
}