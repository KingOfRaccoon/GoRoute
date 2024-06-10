package ru.skittens.domain.usecase

import ru.skittens.domain.repository.UserRepository

class TokenUseCase(private val userRepository: UserRepository) {
    fun getToken() = userRepository.getToken()
}