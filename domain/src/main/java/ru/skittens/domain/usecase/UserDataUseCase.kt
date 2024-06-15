package ru.skittens.domain.usecase

import ru.skittens.domain.entity.AuthenticationUserData
import ru.skittens.domain.entity.RegistrationUserData
import ru.skittens.domain.entity.UserDataToken
import ru.skittens.domain.repository.UserRepository

class UserDataUseCase(private val userRepository: UserRepository) {
    val userFlow = userRepository.userFlow

    suspend fun authenticationUser(userData: AuthenticationUserData) {
        userRepository.authenticationUser(userData)

    }

    suspend fun registrationUser(userData: RegistrationUserData) {
        userRepository.registrationUser(userData)
    }

    suspend fun authenticationUserOnToken() {
            userRepository.authenticationUserOnToken(UserDataToken(userRepository.getToken().orEmpty()))
    }

    fun getToken() = userRepository.getToken()
}