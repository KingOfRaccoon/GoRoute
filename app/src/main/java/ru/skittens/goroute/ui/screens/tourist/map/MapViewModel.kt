package ru.skittens.goroute.ui.screens.tourist.map

import android.location.Location
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.skittens.domain.usecase.CreatePermissionUseCase
import ru.skittens.domain.usecase.GetAreasUseCase
import ru.skittens.domain.usecase.GetFilterAreasUseCase
import ru.skittens.domain.usecase.GetHikeUseCase
import ru.skittens.domain.usecase.GetLevelsUseCase
import ru.skittens.domain.usecase.GetParksUseCase
import ru.skittens.domain.usecase.GetRoutesForChooseUseCase
import ru.skittens.domain.usecase.GetRoutesForSelectedAreaOrPark
import ru.skittens.domain.usecase.GetSelectedRouteUseCase
import ru.skittens.domain.usecase.GetTypesUseCase
import ru.skittens.goroute.LocationTracker

class MapViewModel(
    private val getParksUseCase: GetParksUseCase,
    private val getAreasUseCase: GetAreasUseCase,
    private val getRoutesForSelectedAreaOrPark: GetRoutesForSelectedAreaOrPark,
    private val getRoutesForChooseUseCase: GetRoutesForChooseUseCase,
    private val getLevelsUseCase: GetLevelsUseCase,
    private val getTypesUseCase: GetTypesUseCase,
    private val getFilterAreasUseCase: GetFilterAreasUseCase,
    private val getSelectedRouteUseCase: GetSelectedRouteUseCase,
    private val createPermissionUseCase: CreatePermissionUseCase,
    private val getHikeUseCase: GetHikeUseCase,
    private val locationTracker: LocationTracker
) : ViewModel() {
    val parksFlow = getParksUseCase.parksFlow
    val filteredAreasFlow = getFilterAreasUseCase.filteredAreasFlow
    val areasFlow = getAreasUseCase.areasFlow
    val routesFlow = getRoutesForSelectedAreaOrPark.routesFlow
    val currentIdFlow = getRoutesForSelectedAreaOrPark.selectedIdFlow
    val filtersFlow = getFilterAreasUseCase.filtersFlow
    val currentRoute = getSelectedRouteUseCase.selectedRoute
    val hikeFlow = getHikeUseCase.hikesUserFlow

    // incident
    val levelsFlow = getLevelsUseCase.levels
    val typesFlow = getTypesUseCase.types
    val selectedLevelState = mutableStateOf("")
    val selectedTypeState = mutableStateOf("")
    val descriptionState = mutableStateOf("")

    fun getLevel() = levelsFlow.value.data?.find { it.name == selectedLevelState.value }
    fun getType() = typesFlow.value.data?.find { it.name == selectedTypeState.value }

    var currentLocation by mutableStateOf<Location?>(null)
    var topBarValue by mutableStateOf("")
    var photos = mutableStateListOf<Uri>()

    val typePermissionFlow = createPermissionUseCase.typePermissionFlow
    val dateStartFlow = createPermissionUseCase.dateStartFlow
    val dateEndFlow = createPermissionUseCase.dateEndFlow
    val groupFlow = createPermissionUseCase.groupFlow
    val leaderGroupFlow = createPermissionUseCase.leaderGroupFlow
    val typeWayFlow = createPermissionUseCase.typeWayFlow
    val targetVisitFlow = createPermissionUseCase.targetVisitFlow
    val typePermissionListFlow = createPermissionUseCase.typePermissionListFlow
    val groupListFlow = createPermissionUseCase.groupListFlow
    val leaderGroupListFlow = createPermissionUseCase.leaderGroupListFlow
    val typeWayListFlow = createPermissionUseCase.typeWayListFlow
    val targetVisitListFlow = createPermissionUseCase.targetVisitListFlow

    fun addFilter(newFilter: String) = getFilterAreasUseCase(newFilter)
    fun addFilters(filters: List<String>) = getFilterAreasUseCase.addListFilter(filters)
    fun setRouteId(newId: Int) = getSelectedRouteUseCase(newId)

    fun setTypePermission(newTypePermission: String) =
        createPermissionUseCase.setTypePermission(newTypePermission)
    fun setDateStart(newDateStart: String) =
        createPermissionUseCase.setDateStart(newDateStart)
    fun setDateEnd(newDateEnd: String) =
        createPermissionUseCase.setDateEnd(newDateEnd)
    fun setGroup(newGroup: String) =
        createPermissionUseCase.setGroup(newGroup)
    fun setLeaderGroup(newLeaderGroup: String) =
        createPermissionUseCase.setLeaderGroup(newLeaderGroup)
    fun setTypeWay(newTypeWay: String) =
        createPermissionUseCase.setTypeWay(newTypeWay)
    fun setTargetVisit(newTargetVisit: String) =
        createPermissionUseCase.setTargetVisit(newTargetVisit)

    fun loadLevels() {
        viewModelScope.launch(Dispatchers.IO) {
            getLevelsUseCase()
        }
    }

    fun loadHikes(){
        viewModelScope.launch(Dispatchers.IO) {
            getHikeUseCase()
        }
    }

    fun addHikeGroup(){
        viewModelScope.launch(Dispatchers.IO) {
            createPermissionUseCase(getSelectedRouteUseCase.selectedRouteId.value)
        }
    }

    fun loadTypes() {
        viewModelScope.launch(Dispatchers.IO) {
            getTypesUseCase()
        }
    }

    fun getCurrentLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            currentLocation = locationTracker.getCurrentLocation()
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
        viewModelScope.launch(Dispatchers.IO) {
            getRoutesForSelectedAreaOrPark.loadRoutes()
        }
    }
}