package ru.skittens.goroute.ui.screens.employee.allincidents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.skittens.domain.usecase.IncidentsUseCase

class AllIncidentsViewModel(private val incidentsUseCase: IncidentsUseCase): ViewModel() {
    val incidents = incidentsUseCase.incidents
    val currentIncident = incidentsUseCase.selectedIncident
    
    init {
        viewModelScope.launch(Dispatchers.IO) {  
            incidentsUseCase()
        }
    }

    fun setSelectedId(newId: Int) = incidentsUseCase.setSelectedId(newId)
}