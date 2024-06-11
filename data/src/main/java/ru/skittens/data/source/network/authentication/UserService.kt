package ru.skittens.data.source.network.authentication

import io.ktor.http.*
import ru.kingofraccoons.domain.util.Resource
import ru.skittens.data.util.Postman
import ru.skittens.domain.entity.AuthenticationUserData
import ru.skittens.domain.entity.RegistrationUserData
import ru.skittens.domain.entity.User
import ru.skittens.domain.entity.UserDataToken

class UserService(private val postman: Postman) {
    private val baseUrl = "http://213.171.10.242/api/v1"
    private val authenticationTag = "/auth/authenticate"
    private val registrationTag = "/auth/register"
    private val authenticationOnTokenTag = "/test/authenticateWithToken"

    suspend fun authenticationUser(authenticationUserData: AuthenticationUserData): Resource<User>{
        return postman.post(baseUrl, authenticationTag, authenticationUserData)
    }

    suspend fun registrationUser(registrationUserData: RegistrationUserData): Resource<User> {
        return postman.post(baseUrl, registrationTag, registrationUserData)
    }

    suspend fun authenticationUserOnToken(userDataToken: UserDataToken): Resource<User> {
        return postman.post(baseUrl, authenticationOnTokenTag, null, userDataToken.token.replace("Bearer ", ""))
    }
}