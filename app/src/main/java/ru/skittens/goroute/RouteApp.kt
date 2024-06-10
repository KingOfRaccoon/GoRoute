package ru.skittens.goroute

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ru.skittens.data.manager.TokenManager
import ru.skittens.data.repository.UserRepositoryImpl
import ru.skittens.data.source.network.authentication.UserService
import ru.skittens.data.util.Postman
import ru.skittens.domain.repository.UserRepository
import ru.skittens.domain.usecase.TokenUseCase
import ru.skittens.domain.usecase.UserDataUseCase
import ru.skittens.goroute.ui.screens.start.AuthenticationViewModel
import ru.skittens.goroute.ui.screens.start.authentication.getScreenSize
import ru.skittens.goroute.ui.screens.start.onboarding.OnboardingViewModel

class RouteApp: Application() {
    val modules = module {
        single { getScreenSize(this@RouteApp) }
        single { Postman() }

        single { UserService(get()) }
        single { TokenManager(this@RouteApp) }
        single<UserRepository> { UserRepositoryImpl(get(), get()) }
        single { UserDataUseCase(get()) }
        single { TokenUseCase(get()) }

        single { OnboardingViewModel() }
        single { AuthenticationViewModel(get(), get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(modules)
            androidContext(this@RouteApp)
            androidLogger()
        }
    }
}