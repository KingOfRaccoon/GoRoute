package ru.skittens.goroute.ui.screens.tourist.filterroutes

import androidx.lifecycle.ViewModel
import ru.skittens.domain.usecase.GetRoutesForChooseUseCase

class FilterRoutesViewModel(
    private val getRoutesForChooseUseCase: GetRoutesForChooseUseCase
) : ViewModel() {
    val seasonalityListFlow = getRoutesForChooseUseCase.seasonalityListFlow
    val typeRouteListFlow = getRoutesForChooseUseCase.typeRouteListFlow
    val conditionsListFlow = getRoutesForChooseUseCase.conditionsListFlow
    val typeWayListFlow = getRoutesForChooseUseCase.typeWayListFlow
    val difficultyListFlow = getRoutesForChooseUseCase.difficultyListFlow

    val seasonalityFlow = getRoutesForChooseUseCase.seasonalityFlow
    val typeRouteFlow = getRoutesForChooseUseCase.typeRouteFlow
    val conditionsFlow = getRoutesForChooseUseCase.conditionsFlow
    val typeWayFlow = getRoutesForChooseUseCase.typeWayFlow
    val difficultyFlow = getRoutesForChooseUseCase.difficultyFlow

    fun setSeasonality(newSeasonality: String) =
        getRoutesForChooseUseCase.setSeasonality(newSeasonality)
    fun setTypeRoute(newTypeRoute: String) =
        getRoutesForChooseUseCase.setTypeRoute(newTypeRoute)
    fun setConditions(newConditions: String) =
        getRoutesForChooseUseCase.setConditions(newConditions)
    fun setTypeWay(newTypeWay: String) =
        getRoutesForChooseUseCase.setTypeWay(newTypeWay)
    fun setDifficulty(newDifficulty: String) =
        getRoutesForChooseUseCase.setDifficulty(newDifficulty)
}