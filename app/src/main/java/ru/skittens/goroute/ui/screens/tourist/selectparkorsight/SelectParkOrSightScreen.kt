package ru.skittens.goroute.ui.screens.tourist.selectparkorsight

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.skittens.goroute.ui.elements.ButtonText
import ru.skittens.goroute.ui.navigation.Destinations
import ru.skittens.goroute.ui.navigation.NavigationFun
import ru.skittens.goroute.ui.screens.tourist.routes.NavigateCard

@Composable
fun SelectParkOrSightScreen(navigateTo: NavigationFun) {
    LazyColumn(Modifier.fillMaxSize()) {
        item {
            ParkOrSightItem("Озеро Начикинское") {
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