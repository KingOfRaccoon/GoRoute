package ru.skittens.data.source.network.authentication

import ru.kingofraccoons.domain.util.Resource
import ru.skittens.data.util.Postman
import ru.skittens.domain.entity.AuthenticationUserData
import ru.skittens.domain.entity.RegistrationUserData
import ru.skittens.domain.entity.User
import ru.skittens.domain.entity.UserDataToken

class UserService(private val postman: Postman) {
    val baseUrl = "http://213.171.10.242/api/v1/auth"
    val authenticationTag = "/authenticate"
    val registrationTag = "/register"
    //Todo add auth on token tag
    val authenticationOnTokenTag = "/"

    suspend fun authenticationUser(authenticationUserData: AuthenticationUserData): Resource<User>{
        return postman.post(baseUrl, authenticationTag, authenticationUserData)
    }

    suspend fun registrationUser(registrationUserData: RegistrationUserData): Resource<User> {
        return postman.post(baseUrl, registrationTag, registrationUserData)
    }

    suspend fun authenticationUserOnToken(userDataToken: UserDataToken): Resource<User> {
        return postman.post(baseUrl, authenticationOnTokenTag, userDataToken)
    }
}