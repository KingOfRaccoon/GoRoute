package ru.skittens.goroute

import android.app.Application
import com.mapbox.common.MapboxOptions
import com.mapbox.maps.mapsOptions
import com.mapbox.maps.plugin.Plugin.Mapbox
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ru.skittens.data.manager.TokenManager
import ru.skittens.data.repository.ParkRepositoryImpl
import ru.skittens.data.repository.UserRepositoryImpl
import ru.skittens.data.source.network.authentication.UserService
import ru.skittens.data.source.network.parks.ParkService
import ru.skittens.data.util.Postman
import ru.skittens.domain.repository.ParkRepository
import ru.skittens.domain.repository.UserRepository
import ru.skittens.domain.usecase.*
import ru.skittens.goroute.ui.screens.start.AuthenticationViewModel
import ru.skittens.goroute.ui.screens.start.authentication.getScreenSize
import ru.skittens.goroute.ui.screens.start.onboarding.OnboardingViewModel
import ru.skittens.goroute.ui.screens.tourist.map.MapViewModel
import ru.skittens.goroute.ui.screens.tourist.selectroute.SelectRouteViewModel

class RouteApp: Application() {
    private val modules = module {
        single { getScreenSize(this@RouteApp) }
        single { Postman() }

        single { UserService(get()) }
        single { ParkService(get()) }

        single { TokenManager(this@RouteApp) }
        single<UserRepository> { UserRepositoryImpl(get(), get()) }
        single<ParkRepository> { ParkRepositoryImpl(get()) }

        single { GetParksUseCase(get(), get()) }
        single { GetAreasUseCase(get(), get()) }
        single { GetAreasAndParksUseCase(get(), get()) }
        single { GetRoutesForSelectedAreaOrPark(get(), get()) }
        single { GetRoutesForChooseUseCase(get(), get()) }
        single { UserDataUseCase(get()) }

        single { SelectRouteViewModel(get()) }
        single { OnboardingViewModel() }
        single { AuthenticationViewModel(get()) }
        single { MapViewModel(get(), get(), get(), get()) }
    }

    override fun onCreate() {
        super.onCreate()
        MapboxOptions.accessToken = getString(R.string.mapbox_access_token)
        startKoin {
            modules(modules)
            androidContext(this@RouteApp)
            androidLogger()
        }
    }
}