package ru.skittens.goroute.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.skittens.goroute.ui.screens.admin.MainAdminScreen
import ru.skittens.goroute.ui.screens.employee.MainEmployeeScreen
import ru.skittens.goroute.ui.screens.start.authentication.AuthenticationScreen
import ru.skittens.goroute.ui.screens.start.registration.RegistrationScreen
import ru.skittens.goroute.ui.screens.start.splash.SplashScreen
import ru.skittens.goroute.ui.screens.tourist.MainTouristScreen
import ru.skittens.goroute.ui.theme.GoRouteTheme

typealias NavigationFun = (Destinations) -> Unit

@Composable
fun App() {
    GoRouteTheme {
        val navController = rememberNavController()
        NavHost(navController, Destinations.Authentication.name, Modifier.fillMaxSize()) {
            composable(Destinations.Splash){
                SplashScreen(navController::navigate)
            }

            composable(Destinations.Authentication) {
                AuthenticationScreen(navController::navigate)
            }

            composable(Destinations.Registration) {
                RegistrationScreen(navController::navigate)
            }

            composable(Destinations.MainAdmin) {
                MainAdminScreen()
            }

            composable(Destinations.MainTourist) {
                MainTouristScreen()
            }

            composable(Destinations.MainEmployee) {
                MainEmployeeScreen()
            }
        }
    }
}

fun NavHostController.navigate(destinations: Destinations) {
    navigate(destinations.name)
}

fun NavGraphBuilder.composable(
    destinations: Destinations,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(destinations.name, content = content)
}