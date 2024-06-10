package ru.skittens.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.kingofraccoons.domain.util.Resource
import ru.skittens.data.manager.TokenManager
import ru.skittens.data.source.network.authentication.UserService
import ru.skittens.domain.entity.AuthenticationUserData
import ru.skittens.domain.entity.RegistrationUserData
import ru.skittens.domain.entity.User
import ru.skittens.domain.entity.UserDataToken
import ru.skittens.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userService: UserService,
    private val tokenManager: TokenManager
) : UserRepository {
    private val _userFlow = MutableStateFlow<Resource<User>>(Resource.Loading())
    override val userFlow = _userFlow.asStateFlow()

    override suspend fun authenticationUser(userData: AuthenticationUserData) {
        _userFlow.update {
            userService.authenticationUser(userData).also {
                if (it is Resource.Success)
                    tokenManager.updateToken(it.data.token)
            }
        }
    }

    override suspend fun registrationUser(userData: RegistrationUserData) {
        _userFlow.update {
            userService.registrationUser(userData).also {
                if (it is Resource.Success)
                    tokenManager.updateToken(it.data.token)
            }
        }
    }

    override suspend fun authenticationUserOnToken(userData: UserDataToken) {
        _userFlow.update {
            userService.authenticationUserOnToken(userData).also {
                if (it is Resource.Success)
                    tokenManager.updateToken(it.data.token)
            }
        }
    }

    override fun getToken() = tokenManager.getToken()
}