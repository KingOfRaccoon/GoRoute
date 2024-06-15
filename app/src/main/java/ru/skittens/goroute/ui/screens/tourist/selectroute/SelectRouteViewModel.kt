package ru.skittens.goroute.ui.screens.tourist.selectroute

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.skittens.domain.usecase.GetRoutesForChooseUseCase

class SelectRouteViewModel(private val getRoutesForChooseUseCase: GetRoutesForChooseUseCase): ViewModel() {
    val routes = getRoutesForChooseUseCase.routesForChooseFlow
    
    fun loadRoutes(){
        viewModelScope.launch(Dispatchers.IO) {
            getRoutesForChooseUseCase.loadRoutes()
        }
    }
    
    
}