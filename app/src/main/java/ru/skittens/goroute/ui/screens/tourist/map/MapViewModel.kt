package ru.skittens.goroute.ui.screens.tourist.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.skittens.domain.usecase.*

class MapViewModel(
    private val getParksUseCase: GetParksUseCase,
    private val getAreasUseCase: GetAreasUseCase,
    private val getRoutesForSelectedAreaOrPark: GetRoutesForSelectedAreaOrPark,
    private val getRoutesForChooseUseCase: GetRoutesForChooseUseCase
) : ViewModel() {
    val parksFlow = getParksUseCase.parksFlow
    val areasFlow = getAreasUseCase.areasFlow
    val routesFlow = getRoutesForSelectedAreaOrPark.routesFlow
    val currentIdFlow = getRoutesForSelectedAreaOrPark.selectedIdFlow

    fun loadParksAndAreas() {
        viewModelScope.launch(Dispatchers.IO) {
            getParksUseCase()
        }
        
        viewModelScope.launch(Dispatchers.IO) {
            getAreasUseCase()
        }
    }
    
    fun setAreaId(newId: String?) = getRoutesForChooseUseCase.setId(newId)
    
    fun setAreaOrParkId(selectedId: String) = getRoutesForSelectedAreaOrPark(selectedId)
    
    fun loadRoutes() {
        viewModelScope.launch(Dispatchers.IO){
            getRoutesForSelectedAreaOrPark.loadRoutes()
        }
    }
}