package ru.skittens.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.kingofraccoons.domain.util.Resource
import ru.skittens.data.source.network.incidents.IncidentsService
import ru.skittens.domain.entity.EmployeeStatus
import ru.skittens.domain.entity.Incident
import ru.skittens.domain.entity.IncidentRequest
import ru.skittens.domain.entity.Status
import ru.skittens.domain.repository.IncidentRepository

class IncidentRepositoryImpl(private val incidentsService: IncidentsService): IncidentRepository {
    private val _incidentsFlow = MutableStateFlow<Resource<List<Incident>>>(Resource.Loading())
    override val incidents: StateFlow<Resource<List<Incident>>> = _incidentsFlow.asStateFlow()

    override suspend fun loadIncidents(token: String) {
        _incidentsFlow.update {
            incidentsService.getIncidents(token)
        }
    }

    override suspend fun addIncident(incident: IncidentRequest, file: ByteArray, token: String): Resource<Incident> {
        return incidentsService.addIncident(incident, file, token)
    }

    override suspend fun updateStatus(status: Status, token: String): Resource<String> {
        return incidentsService.updateStatus(status, token)
    }

    override suspend fun assignEmployee(employeeStatus: EmployeeStatus, token: String): Resource<String> {
        return incidentsService.assignEmployee(employeeStatus, token)
    }
}