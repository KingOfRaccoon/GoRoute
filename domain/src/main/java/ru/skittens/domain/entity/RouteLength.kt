package ru.skittens.domain.entity

import java.util.UUID

data class RouteLength(
    val map: MutableMap<Int, Double> = mutableMapOf(),
    val uuid: UUID = UUID.randomUUID()
)