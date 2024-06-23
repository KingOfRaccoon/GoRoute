package ru.skittens.goroute.ui.screens.tourist.addincident

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.skittens.domain.entity.Incident
import ru.skittens.domain.entity.IncidentRequest
import ru.skittens.domain.usecase.IncidentsUseCase

class AddIncidentViewModel(private val incidentsUseCase: IncidentsUseCase): ViewModel() {
    fun addIncident(incident: IncidentRequest, file: ByteArray) {
        viewModelScope.launch(Dispatchers.IO) {
            incidentsUseCase.addIncident(incident, file)
        }
    }
}