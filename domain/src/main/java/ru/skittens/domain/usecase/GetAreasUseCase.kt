package ru.skittens.domain.usecase

import ru.skittens.domain.repository.ParkRepository
import ru.skittens.domain.repository.UserRepository

class GetAreasUseCase(private val userRepository: UserRepository, private val parkRepository: ParkRepository) {
    val areasFlow = parkRepository.areasFlow
    
    suspend operator fun invoke(){
        parkRepository.getAreas(userRepository.getToken().orEmpty())
    }
}