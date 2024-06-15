package ru.skittens.goroute.ui.screens.tourist.map

enum class BottomSheetState(var previousState: BottomSheetState?) {
    ParkOrArea(null),
    RoutesParkOrArea(ParkOrArea)
}