package ru.skittens.goroute

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}