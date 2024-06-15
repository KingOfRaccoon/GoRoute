package ru.skittens.goroute.ui.screens.tourist

import androidx.annotation.DrawableRes
import ru.skittens.goroute.R
import ru.skittens.goroute.ui.navigation.Destinations

enum class TouristNavigationBarItem(
    val destinations: Destinations,
    val title: String,
    @DrawableRes val icon: Int
) {
    Map(Destinations.Map, "Карта", R.drawable.ic_map),
    Routes(Destinations.Routes, "Поход", R.drawable.ic_routes),
    Profile(Destinations.Profile, "Профиль", R.drawable.ic_profile)
}