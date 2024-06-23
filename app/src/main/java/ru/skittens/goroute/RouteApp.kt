package ru.skittens.goroute

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mapbox.common.MapboxOptions
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ru.skittens.data.manager.TokenManager
import ru.skittens.data.repository.IncidentRepositoryImpl
import ru.skittens.data.repository.ParkRepositoryImpl
import ru.skittens.data.repository.UserRepositoryImpl
import ru.skittens.data.source.network.authentication.UserService
import ru.skittens.data.source.network.incidents.IncidentsService
import ru.skittens.data.source.network.parks.ParkService
import ru.skittens.data.util.Postman
import ru.skittens.domain.repository.IncidentRepository
import ru.skittens.domain.repository.ParkRepository
import ru.skittens.domain.repository.UserRepository
import ru.skittens.domain.usecase.*
import ru.skittens.goroute.ui.screens.employee.allincidents.AllIncidentsViewModel
import ru.skittens.goroute.ui.screens.start.AuthenticationViewModel
import ru.skittens.goroute.ui.screens.start.authentication.getScreenSize
import ru.skittens.goroute.ui.screens.start.onboarding.OnboardingViewModel
import ru.skittens.goroute.ui.screens.tourist.filterroutes.FilterRoutesViewModel
import ru.skittens.goroute.ui.screens.tourist.map.MapViewModel
import ru.skittens.goroute.ui.screens.tourist.selectroute.SelectRouteViewModel

class RouteApp : Application() {
    private val modules = module {
        single { getScreenSize(this@RouteApp) }
        single { Postman() }

        single { UserService(get()) }
        single { ParkService(get()) }
        single { IncidentsService(get()) }

        single { TokenManager(this@RouteApp) }
        single<UserRepository> { UserRepositoryImpl(get(), get()) }
        single<ParkRepository> { ParkRepositoryImpl(get()) }
        single<IncidentRepository> { IncidentRepositoryImpl(get()) }
        single<FusedLocationProviderClient> { LocationServices.getFusedLocationProviderClient(this@RouteApp) }
        single<LocationTracker> {DefaultLocationTracker(get(), this@RouteApp)}

        single { GetParksUseCase(get(), get()) }
        single { GetAreasUseCase(get(), get()) }
        single { GetLevelsUseCase(get(), get()) }
        single { GetTypesUseCase(get(), get()) }
        single { GetParksAndAreasUseCase(get(), get()) }
        single { GetRoutesForSelectedAreaOrPark(get(), get()) }
        single { GetRoutesForChooseUseCase(get(), get()) }
        single { UserDataUseCase(get()) }
        single { IncidentsUseCase(get(), get()) }
        single { GetFilterAreasUseCase(get()) }

        single { SelectRouteViewModel(get()) }
        single { OnboardingViewModel() }
        single { AuthenticationViewModel(get()) }
        single { MapViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }
        single { AllIncidentsViewModel(get()) }
        single { FilterRoutesViewModel(get()) }
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