package ru.skittens.domain.usecase

import ru.skittens.domain.repository.HikeRepository
import ru.skittens.domain.repository.UserRepository

class GetHikeUseCase(
    private val userRepository: UserRepository,
    private val hikeRepository: HikeRepository
) {
    val hikesUserFlow = hikeRepository.groupsUserFlow

    suspend operator fun invoke(idUser: Int) {
        hikeRepository.loadGroup(idUser, userRepository.getToken().orEmpty())
    }
}