package ru.skittens.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class CreatePermissionUseCase {
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

    fun setTypePermission(newTypePermission: String) = typePermissionFlow.update { newTypePermission }
    fun setDateStart(newDateStart: String) = dateStartFlow.update { newDateStart }
    fun setDateEnd(newDateEnd: String) = dateEndFlow.update { newDateEnd }
    fun setGroup(newGroup: String) = groupFlow.update { newGroup }
    fun setLeaderGroup(newLeaderGroup: String) = leaderGroupFlow.update { newLeaderGroup }
    fun setTypeWay(newTypeWay: String) = typeWayFlow.update { newTypeWay }
    fun setTargetVisit(newTargetVisit: String) = targetVisitFlow.update { newTargetVisit }
}