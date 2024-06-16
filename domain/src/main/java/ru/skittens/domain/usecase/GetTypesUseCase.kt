package ru.skittens.domain.usecase

import ru.skittens.domain.repository.ParkRepository
import ru.skittens.domain.repository.UserRepository

class GetTypesUseCase(private val userRepository: UserRepository, private val parkRepository: ParkRepository) {
    val types = parkRepository.typesFlow
    
    suspend operator fun invoke(){
        parkRepository.getTypeIncident(userRepository.getToken().orEmpty())
    }  
}