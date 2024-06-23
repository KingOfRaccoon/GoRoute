package ru.skittens.domain.repository

import kotlinx.coroutines.flow.StateFlow
import ru.kingofraccoons.domain.util.Resource
import ru.skittens.domain.entity.EmployeeStatus
import ru.skittens.domain.entity.Incident
import ru.skittens.domain.entity.IncidentRequest
import ru.skittens.domain.entity.Status

interface IncidentRepository {
    val incidents: StateFlow<Resource<List<Incident>>>
    
    suspend fun loadIncidents(token: String)
    suspend fun addIncident(incident: IncidentRequest, file: ByteArray, token: String) : Resource<Incident>
    suspend fun updateStatus(status: Status, token: String) : Resource<String>
    suspend fun assignEmployee(employeeStatus: EmployeeStatus, token: String) : Resource<String>
}