package com.epicActivities.domain.usecase

import com.epicActivities.domain.model.Activity
import com.epicActivities.domain.model.SportType
import kotlinx.coroutines.delay

class GetActivitiesUseCase {
    suspend operator fun invoke(): List<Activity> {
        delay(3_000)
        return fakeActivities
    }

    private val fakeActivities = listOf(
        Activity(
            id = "1",
            title = "Carrera matutina en el parque",
            date = "10 jun 2026",
            sportType = SportType.Run,
            polyline = "gcpyHfmioVrBqA`@e@zAgBr@_A",
        ),
        Activity(
            id = "2",
            title = "Vuelta en bici por la montaña",
            date = "8 jun 2026",
            sportType = SportType.Ride,
            polyline = "gcpyHfmioVrBqA`@e@zAgBr@_A~@kAv@_AXa@",
        ),
        Activity(
            id = "3",
            title = "Caminata vespertina",
            date = "5 jun 2026",
            sportType = SportType.Walk,
            polyline = "gcpyHfmioVrBqA`@e@",
        ),
        Activity(
            id = "4",
            title = "Trail running en el bosque",
            date = "2 jun 2026",
            sportType = SportType.Hike,
            polyline = "gcpyHfmioVrBqA",
        ),
        Activity(
            id = "5",
            title = "Natación en la piscina olímpica",
            date = "1 jun 2026",
            sportType = SportType.Swim,
            polyline = "gcpyHfmioVrBqA`@e@zAgB",
        ),
    )
}
