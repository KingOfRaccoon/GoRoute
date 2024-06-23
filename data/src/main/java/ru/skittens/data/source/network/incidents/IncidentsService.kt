package ru.skittens.data.source.network.incidents

import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import ru.kingofraccoons.domain.util.Resource
import ru.skittens.data.util.Postman
import ru.skittens.domain.entity.EmployeeStatus
import ru.skittens.domain.entity.Incident
import ru.skittens.domain.entity.IncidentRequest
import ru.skittens.domain.entity.Status

class IncidentsService(private val postman: Postman) {
    private val baseUrl = "http://213.171.10.242/api/v1"
    private val incidentsTag = "/incidents"
    private val addIncidentTag = "/addIncident"
    private val updateIncidentStatusTag = "/updateIncidentStatus"
    private val assignEmployeeTag = "/assignEmployeeToSolveIncident"

    suspend fun getIncidents(token: String): Resource<List<Incident>> {
        return postman.get(baseUrl, incidentsTag, token = token)
    }

    suspend fun addIncident(
        incident: IncidentRequest,
        file: ByteArray,
        token: String
    ): Resource<Incident> {
        return postman.submitFormWithBinaryFiles<Incident>(
            baseUrl,
            addIncidentTag,
            body = incident,
            token = token,
            arguments = formData {
                if (file.isNotEmpty())
                    append("file", file, Headers.build {
                        append(HttpHeaders.ContentType, "image/jpeg")
                    })
            }
        ).also { println("addIncident: $it") }
    }

    suspend fun updateStatus(status: Status, token: String): Resource<String> {
        return postman.post(baseUrl, updateIncidentStatusTag, status, token)
    }

    suspend fun assignEmployee(employeeStatus: EmployeeStatus, token: String): Resource<String> {
        return postman.post(baseUrl, assignEmployeeTag, employeeStatus, token = token)
    }
}