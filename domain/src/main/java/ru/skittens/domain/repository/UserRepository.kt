package ru.skittens.domain.repository

import kotlinx.coroutines.flow.StateFlow
import ru.kingofraccoons.domain.util.Resource
import ru.skittens.domain.entity.AuthenticationUserData
import ru.skittens.domain.entity.RegistrationUserData
import ru.skittens.domain.entity.User
import ru.skittens.domain.entity.UserDataToken

interface UserRepository {

    val userFlow: StateFlow<Resource<User>>
    suspend fun authenticationUser(userData: AuthenticationUserData)
    suspend fun registrationUser(userData: RegistrationUserData)
    suspend fun authenticationUserOnToken(userData: UserDataToken)
    fun getToken(): String?
}