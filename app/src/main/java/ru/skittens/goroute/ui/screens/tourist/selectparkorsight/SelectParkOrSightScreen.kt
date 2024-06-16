package ru.skittens.goroute.ui.screens.tourist.selectparkorsight

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import ru.skittens.goroute.ui.elements.ButtonText
import ru.skittens.goroute.ui.navigation.Destinations
import ru.skittens.goroute.ui.navigation.NavigationFun
import ru.skittens.goroute.ui.screens.tourist.map.MapViewModel
import ru.skittens.goroute.ui.screens.tourist.routes.NavigateCard

@Composable
fun SelectParkOrSightScreen(navigateTo: NavigationFun, viewModel: MapViewModel = koinInject()) {
    val areas by viewModel.areasFlow.collectAsState()
    LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(areas.data.orEmpty()) {
            ParkOrSightItem(it.name + " " + it.subname) {
                viewModel.setAreaId(it.id)
                viewModel.topBarValue = it.name
                navigateTo(Destinations.SelectRoute)
            }
        }
    }
}

@Composable
fun ParkOrSightItem(text: String, onClick: () -> Unit) {
    NavigateCard(onClick) {
        ButtonText(text)
    }
}