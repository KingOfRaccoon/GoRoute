package ru.skittens.goroute.ui.screens.employee

import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import ru.skittens.goroute.ui.elements.BodyText
import ru.skittens.goroute.ui.navigation.Destinations
import ru.skittens.goroute.ui.navigation.composable
import ru.skittens.goroute.ui.navigation.navigate
import ru.skittens.goroute.ui.screens.employee.allincidents.AllIncidentsScreen
import ru.skittens.goroute.ui.screens.tourist.DefaultTopBar

@Composable
fun MainEmployeeScreen() {
    val navHostController = rememberNavController()
    val currentEntity by navHostController.currentBackStackEntryAsState()
    val currentDestinations = remember(currentEntity) {
        Destinations.entries.find { it.name == currentEntity?.destination?.route } ?: Destinations.Map
    }

    Scaffold(
        Modifier.fillMaxSize(),
        topBar = { DefaultTopBar() },
        bottomBar = {
            AnimatedVisibility(
                currentEntity?.destination?.route in EmployeeNavigationBarItem.entries.map { it.destinations.name },
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
                    EmployeeNavigationBarItem.entries.toTypedArray().forEach {
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
            Destinations.AllIncidents.name,
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            composable(Destinations.MapEmployee){

            }

            composable(Destinations.AllIncidents){
                AllIncidentsScreen()
            }

            composable(Destinations.ProfileEmployee){

            }
        }
    }
}