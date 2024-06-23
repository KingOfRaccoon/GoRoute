package ru.skittens.domain.usecase

import kotlinx.coroutines.flow.combine
import ru.skittens.domain.repository.ParkRepository
import ru.skittens.domain.repository.UserRepository
import kotlin.math.abs

class GetParksAndAreasUseCase(private val userRepository: UserRepository, private val parkRepository: ParkRepository) {
    private val areasFlow = parkRepository.areasFlow
    private val parksFlow = parkRepository.parksFlow

    val diffFlow = combine(areasFlow, parksFlow) { areas, parks ->
        areas.data?.associateBy { area ->
            parks.data?.find { park ->
                area.getGeometryData().areas.any {
                    polygonsIntersect(
                        it.coordinates.flatten().map { it.reversed() },
                        park.getPointsList()
                    )
                }
            }
        }
    }

    suspend operator fun invoke() {
        parkRepository.getAreas(userRepository.getToken().orEmpty())
    }

    fun calculateArea(points: List<List<Double>>): Double {
        val n = points.size
        var area = 0.0

        for (i in 0 until n) {
            val j = (i + 1) % n
            area += points[i][1] * points[j][0]
            area -= points[j][1] * points[i][0]
        }

        return abs(area) / 2.0
    }

    fun polygonsIntersect(
        firstPolygonPoints: List<List<Double>>,
        secondPolygonPoints: List<List<Double>>
    ): Boolean {
        // Простейшая проверка пересечения полигонов
        for (i in firstPolygonPoints.indices) {
            for (j in secondPolygonPoints.indices) {
                if (linesIntersect(
                        firstPolygonPoints[i],
                        firstPolygonPoints[(i + 1) % firstPolygonPoints.size],
                        secondPolygonPoints[j],
                        secondPolygonPoints[(j + 1) % secondPolygonPoints.size]
                    )
                ) {
                    return true
                }
            }
        }
        return false
    }

    // Функция для проверки пересечения двух отрезков
    fun linesIntersect(p1: List<Double>, p2: List<Double>, p3: List<Double>, p4: List<Double>): Boolean {
        val d1 = direction(p3, p4, p1)
        val d2 = direction(p3, p4, p2)
        val d3 = direction(p1, p2, p3)
        val d4 = direction(p1, p2, p4)

        if (((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) &&
            ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0))
        ) {
            return true
        }

        return (d1 == 0 && onSegment(p3, p4, p1)) ||
            (d2 == 0 && onSegment(p3, p4, p2)) ||
            (d3 == 0 && onSegment(p1, p2, p3)) ||
            (d4 == 0 && onSegment(p1, p2, p4))
    }

    // Функция для вычисления направления
    fun direction(p1: List<Double>, p2: List<Double>, p3: List<Double>): Int {
        val val1 = (p2[1] - p1[1]) * (p3[0] - p1[0])
        val val2 = (p3[1] - p1[1]) * (p2[0] - p1[0])
        return when {
            val1 > val2 -> 1
            val1 < val2 -> -1
            else -> 0
        }
    }

    // Функция для проверки, находится ли точка на отрезке
    fun onSegment(p1: List<Double>, p2: List<Double>, p: List<Double>): Boolean {
        return p[1] >= minOf(p1[1], p2[1]) && p[1] <= maxOf(p1[1], p2[1]) &&
            p[0] >= minOf(p1[0], p2[0]) && p[0] <= maxOf(p1[0], p2[0])
    }
}