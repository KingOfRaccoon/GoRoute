package ru.skittens.goroute.ui.screens.tourist.map

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.skittens.domain.entity.Level
import ru.skittens.domain.entity.Type
import ru.skittens.domain.usecase.*
import ru.skittens.goroute.LocationTracker

class MapViewModel(
    private val getParksUseCase: GetParksUseCase,
    private val getAreasUseCase: GetAreasUseCase,
    private val getRoutesForSelectedAreaOrPark: GetRoutesForSelectedAreaOrPark,
    private val getRoutesForChooseUseCase: GetRoutesForChooseUseCase,
    private val getLevelsUseCase: GetLevelsUseCase,
    private val getTypesUseCase: GetTypesUseCase,
    private val locationTracker: LocationTracker
) : ViewModel() {
    val parksFlow = getParksUseCase.parksFlow
    val areasFlow = getAreasUseCase.areasFlow
    val routesFlow = getRoutesForSelectedAreaOrPark.routesFlow
    val currentIdFlow = getRoutesForSelectedAreaOrPark.selectedIdFlow
    val levelsFlow = getLevelsUseCase.levels
    val typesFlow = getTypesUseCase.types
    val selectedLevelState = mutableStateOf("")
    val selectedTypeState = mutableStateOf("")
    val descriptionState = mutableStateOf("")
    var currentLocation by mutableStateOf<Location?>(null)
    var topBarValue by mutableStateOf("")

    fun loadLevels(){
        viewModelScope.launch(Dispatchers.IO) {
            getLevelsUseCase()
        }
    }

    fun loadTypes(){
        viewModelScope.launch(Dispatchers.IO) {
            getTypesUseCase()
        }
    }

    fun getCurrentLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            currentLocation = locationTracker.getCurrentLocation() // Location
        }
    }

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