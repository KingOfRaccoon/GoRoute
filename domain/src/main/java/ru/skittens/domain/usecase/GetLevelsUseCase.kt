package ru.skittens.domain.usecase

import ru.skittens.domain.repository.ParkRepository
import ru.skittens.domain.repository.UserRepository

class GetLevelsUseCase(private val userRepository: UserRepository, private val parkRepository: ParkRepository) {
    val levels = parkRepository.levelsFlow
    
    suspend operator fun invoke(){
        parkRepository.getLevelIncident(userRepository.getToken().orEmpty())
    }
}