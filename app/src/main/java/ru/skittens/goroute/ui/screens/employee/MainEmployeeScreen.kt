package ru.skittens.goroute.ui.screens.employee

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.util.fastFirstOrNull
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.skittens.goroute.ui.navigation.Destinations

@Composable
fun MainEmployeeScreen() {
    val navHostController = rememberNavController()
    val currentEntity = remember(navHostController.currentDestination?.route) {
        Destinations.entries.toList()
            .fastFirstOrNull { it.name == navHostController.currentDestination?.route }
    }

    Scaffold(
        Modifier.fillMaxSize()
    ) {
        NavHost(
            navHostController, Destinations.Map.name,
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(title: String) {
    TopAppBar(
        {  },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF000000))
    )
}