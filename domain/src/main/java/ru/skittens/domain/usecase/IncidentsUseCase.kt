package ru.skittens.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import ru.skittens.domain.entity.EmployeeStatus
import ru.skittens.domain.entity.IncidentRequest
import ru.skittens.domain.entity.Status
import ru.skittens.domain.repository.IncidentRepository
import ru.skittens.domain.repository.UserRepository

class IncidentsUseCase(
    private val userRepository: UserRepository,
    private val incidentRepository: IncidentRepository
) {
    val incidents = incidentRepository.incidents
    val selectedIdIncident = MutableStateFlow(-1)

    val selectedIncident = combine(incidents, selectedIdIncident) { incidentsList, selectedId ->
        incidentsList.data?.find { it.id == selectedId }
    }

    suspend operator fun invoke() {
        incidentRepository.loadIncidents(userRepository.getToken().orEmpty())
    }

    fun setSelectedId(newId: Int) = selectedIdIncident.update { newId }

    suspend fun addIncident(incident: IncidentRequest, file: ByteArray) =
        incidentRepository.addIncident(incident, file, userRepository.getToken().orEmpty())

    suspend fun updateStatus(status: Status) =
        incidentRepository.updateStatus(status, userRepository.getToken().orEmpty())

    suspend fun assignEmployee(employeeStatus: EmployeeStatus) =
        incidentRepository.assignEmployee(employeeStatus, userRepository.getToken().orEmpty())
}