package ru.skittens.domain.usecase

import ru.skittens.domain.repository.ParkRepository
import ru.skittens.domain.repository.UserRepository

class GetParksUseCase(private val userRepository: UserRepository, private val parkRepository: ParkRepository) {
    val parksFlow = parkRepository.parksFlow
    
    suspend operator fun invoke(){
        parkRepository.getParks(userRepository.getToken().orEmpty())
    }
}