package ru.skittens.goroute.ui.screens.employee

import androidx.annotation.DrawableRes
import ru.skittens.goroute.R
import ru.skittens.goroute.ui.navigation.Destinations

enum class EmployeeNavigationBarItem(
    val destinations: Destinations,
    val title: String,
    @DrawableRes val icon: Int
) {
    Map(Destinations.Map, "Карта", R.drawable.ic_map),
    AllIncidents(Destinations.AllIncidents, "Инциденты", R.drawable.ic_warning),
    Profile(Destinations.Profile, "Профиль", R.drawable.ic_profile)
}