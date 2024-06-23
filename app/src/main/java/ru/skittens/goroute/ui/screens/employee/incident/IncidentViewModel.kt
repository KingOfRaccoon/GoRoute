package ru.skittens.goroute.ui.screens.employee.incident

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.skittens.domain.entity.IncidentRequest
import ru.skittens.domain.usecase.IncidentsUseCase

class IncidentViewModel(private val incidentsUseCase: IncidentsUseCase): ViewModel() {
    fun addIncident(incident: IncidentRequest, file: ByteArray) {
        viewModelScope.launch(Dispatchers.IO) {
            incidentsUseCase.addIncident(incident, file)
        }
    }
}