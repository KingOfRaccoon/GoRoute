package ru.skittens.goroute.ui.screens.start

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.skittens.domain.entity.AuthenticationUserData
import ru.skittens.domain.entity.RegistrationUserData
import ru.skittens.domain.usecase.UserDataUseCase

class AuthenticationViewModel(
    private val userDataUseCase: UserDataUseCase,
) : ViewModel() {
    val user = userDataUseCase.userFlow

    var login by mutableStateOf("")
    var password by mutableStateOf("")
    var fullName by mutableStateOf("")

    fun authenticationUser() {
        viewModelScope.launch(Dispatchers.IO) {
            userDataUseCase.authenticationUser(AuthenticationUserData(login, password))
        }
    }

    fun registrationUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val splitName = fullName.split(" ")
            userDataUseCase.registrationUser(
                RegistrationUserData(
                    login,
                    splitName.firstOrNull().orEmpty(),
                    splitName.lastOrNull().orEmpty(),
                    password
                )
            )
        }
    }

    fun authenticationUserOnToken() {
        viewModelScope.launch(Dispatchers.IO) {
            userDataUseCase.authenticationUserOnToken()
        }
    }

    fun getToken() = userDataUseCase.getToken()
}