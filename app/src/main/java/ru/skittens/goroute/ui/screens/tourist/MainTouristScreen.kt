package ru.skittens.goroute.ui.screens.tourist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.koin.compose.koinInject
import ru.skittens.goroute.R
import ru.skittens.goroute.ui.elements.BodyText
import ru.skittens.goroute.ui.elements.TitleText
import ru.skittens.goroute.ui.navigation.Destinations
import ru.skittens.goroute.ui.navigation.composable
import ru.skittens.goroute.ui.navigation.navigate
import ru.skittens.goroute.ui.screens.tourist.route.RouteScreen
import ru.skittens.goroute.ui.screens.tourist.addgroup.AddGroupScreen
import ru.skittens.goroute.ui.screens.tourist.addincident.AddIncidentScreen
import ru.skittens.goroute.ui.screens.tourist.endedroutes.EndedRoutesScreen
import ru.skittens.goroute.ui.screens.tourist.filterroutes.FilterRoutesScreen
import ru.skittens.goroute.ui.screens.tourist.group.GroupScreen
import ru.skittens.goroute.ui.screens.tourist.map.MapScreen
import ru.skittens.goroute.ui.screens.tourist.map.MapViewModel
import ru.skittens.goroute.ui.screens.tourist.newroute.NewRouteScreen
import ru.skittens.goroute.ui.screens.tourist.permission.PermissionScreen
import ru.skittens.goroute.ui.screens.tourist.profile.ProfileScreen
import ru.skittens.goroute.ui.screens.tourist.routes.RoutesScreen
import ru.skittens.goroute.ui.screens.tourist.selectparkorsight.SelectParkOrSightScreen
import ru.skittens.goroute.ui.screens.tourist.selectroute.SelectRouteScreen

@Composable
fun MainTouristScreen(viewModel: MapViewModel = koinInject()) {
    val navHostController = rememberNavController()
    val currentEntity by navHostController.currentBackStackEntryAsState()
    val currentDestinations = remember(currentEntity) {
        Destinations.entries.find { it.name == currentEntity?.destination?.route }
            ?: Destinations.Map
    }

    Scaffold(
        Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            MainTopBar(
                currentDestinations,
                viewModel.topBarValue
            ) { navHostController.popBackStack() }
        },
        bottomBar = {
            AnimatedVisibility(
                currentEntity?.destination?.route in arrayOf(
                    TouristNavigationBarItem.Map.destinations.name,
                    TouristNavigationBarItem.Routes.destinations.name,
                    TouristNavigationBarItem.Profile.destinations.name
                ),
                exit = slideOutVertically { it } + shrinkVertically { 0 },
                enter = slideInVertically { it } + expandVertically { 0 }
            ) {
                NavigationBar(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp)
                        .shadow(
                            elevation = 24.dp,
                            spotColor = Color(0x0D000000),
                            ambientColor = Color(0x0D000000)
                        )
                        .clip(RoundedCornerShape(30.dp)),
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ) {
                    TouristNavigationBarItem.entries.toTypedArray().forEach {
                        NavigationBarItem(
                            it.destinations.name == currentEntity?.destination?.route,
                            { navHostController.navigate(it.destinations) },
                            { Icon(painterResource(it.icon), null) },
                            label = {
                                BodyText(it.title, color = MaterialTheme.colorScheme.primary)
                            },
                            alwaysShowLabel = false,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    }
                }
            }
        }
    ) {
        NavHost(
            navHostController,
            Destinations.Routes.name,
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            composable(Destinations.Routes) {
                RoutesScreen(navHostController::navigate)
            }

            composable(Destinations.Route){
                RouteScreen(navHostController::navigate)
            }

            composable(Destinations.NewRoute) {
                NewRouteScreen(navHostController::navigate)
            }

            composable(Destinations.SelectParkOrSight) {
                SelectParkOrSightScreen(navHostController::navigate)
            }

            composable(Destinations.SelectRoute) {
                SelectRouteScreen(navHostController::navigate)
            }

            composable(Destinations.EndedRoutes) {
                EndedRoutesScreen()
            }

            composable(Destinations.Group) {
                GroupScreen(navHostController::navigate)
            }

            composable(Destinations.FiltersRoutes) {
                FilterRoutesScreen()
            }

            composable(Destinations.AddGroup) {
                AddGroupScreen(navHostController::navigate)
            }

            composable(Destinations.Map) {
                MapScreen(navHostController::navigate)
            }

            composable(Destinations.Permission) {
                PermissionScreen(navHostController::navigate, navHostController::popBackStack)
            }

            composable(Destinations.AddIncident) {
                AddIncidentScreen(navHostController::popBackStack)
            }

            composable(Destinations.Profile) {
                ProfileScreen()
            }
        }
    }
}

@Composable
fun MainTopBar(destinations: Destinations, topBarValue: String, onBackStack: () -> Unit) {
//    AnimatedContent(destinations) {
    when (destinations) {
        Destinations.Group, Destinations.FiltersRoutes, Destinations.Permission, Destinations.AddIncident, Destinations.NewRoute, Destinations.EndedRoutes, Destinations.SelectRoute, Destinations.SelectParkOrSight, Destinations.Route, Destinations.AddGroup -> {
            BackTopBar(destinations, topBarValue, onBackStack)
        }

        Destinations.Profile, Destinations.Routes -> {
            DefaultTopBar()
        }

        Destinations.Map -> {}
        Destinations.NewsFriends -> {}
        else -> {}
    }
//    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DefaultTopBar() {
    TopAppBar(
        {},
        Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        { Image(painterResource(R.drawable.logo_small), null) },
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent)
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun BackTopBar(destinations: Destinations, topBarValue: String, onBackStack: () -> Unit) {
    TopAppBar(
        {
            TitleText(destinations.title ?: topBarValue)
        },
        Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        { IconButton(onBackStack) { Image(Icons.AutoMirrored.Filled.ArrowBack, null) } },
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent)
    )
}