package ru.skittens.domain.usecase

import ru.skittens.domain.entity.EmployeeStatus
import ru.skittens.domain.entity.Incident
import ru.skittens.domain.entity.Status
import ru.skittens.domain.repository.IncidentRepository
import ru.skittens.domain.repository.UserRepository

class IncidentsUseCase(
    private val userRepository: UserRepository,
    private val incidentRepository: IncidentRepository
) {
    val incidents = incidentRepository.incidents

    suspend operator fun invoke() {
        incidentRepository.loadIncidents(userRepository.getToken().orEmpty())
    }

    suspend fun addIncident(incident: Incident) =
        incidentRepository.addIncident(incident, userRepository.getToken().orEmpty())

    suspend fun updateStatus(status: Status) =
        incidentRepository.updateStatus(status, userRepository.getToken().orEmpty())

    suspend fun assignEmployee(employeeStatus: EmployeeStatus) =
        incidentRepository.assignEmployee(employeeStatus, userRepository.getToken().orEmpty())
}