package ru.skittens.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ru.skittens.domain.entity.HikeGroupRequest
import ru.skittens.domain.repository.HikeRepository
import ru.skittens.domain.repository.ParkRepository
import ru.skittens.domain.repository.UserRepository

class CreatePermissionUseCase(
    private val userRepository: UserRepository,
    private val hikeRepository: HikeRepository,
    private val parkRepository: ParkRepository
) {
    val typePermissionFlow = MutableStateFlow("")
    val dateStartFlow = MutableStateFlow("")
    val dateEndFlow = MutableStateFlow("")
    val groupFlow = MutableStateFlow("")
    val leaderGroupFlow = MutableStateFlow("")
    val typeWayFlow = MutableStateFlow("")
    val targetVisitFlow = MutableStateFlow("")

    val typePermissionListFlow = MutableStateFlow(List(3) { "Тип разрешения $it" })
    val groupListFlow = MutableStateFlow(List(3) { "Группа $it" })
    val leaderGroupListFlow = MutableStateFlow(List(3) { "Лидер группы $it" })
    val typeWayListFlow = MutableStateFlow(List(3) { "Тип перемешения $it" })
    val targetVisitListFlow = MutableStateFlow(List(3) { "Цель визита $it" })

    fun setTypePermission(newTypePermission: String) =
        typePermissionFlow.update { newTypePermission }

    fun setDateStart(newDateStart: String) = dateStartFlow.update { newDateStart }
    fun setDateEnd(newDateEnd: String) = dateEndFlow.update { newDateEnd }
    fun setGroup(newGroup: String) = groupFlow.update { newGroup }
    fun setLeaderGroup(newLeaderGroup: String) = leaderGroupFlow.update { newLeaderGroup }
    fun setTypeWay(newTypeWay: String) = typeWayFlow.update { newTypeWay }
    fun setTargetVisit(newTargetVisit: String) = targetVisitFlow.update { newTargetVisit }

    suspend operator fun invoke(routeId: Int) = hikeRepository.addHikeGroup(
        HikeGroupRequest(
            dateStartFlow.value.split(".").reversed().joinToString("-") { it } + "T00:00:00",
            dateEndFlow.value.split(".").reversed().joinToString("-") { it } + "T00:00:00",
            userRepository.userFlow.value.data?.id ?: 12,
            routeId
        ), userRepository.getToken().orEmpty()
    )
}