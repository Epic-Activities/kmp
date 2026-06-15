package com.epicActivities.domain.usecase

import com.epicActivities.domain.model.Activity
import com.epicActivities.domain.model.SportType
import kotlinx.coroutines.delay

class GetActivitiesUseCase {
    suspend operator fun invoke(): List<Activity> {
        delay(3_000)
        return fakeActivities
    }

    // Polylines use Google Encoded Polyline format.
    // Each delta segment: gEgE=(+0.001,+0.001) NE, cBz@=(+0.0005,-0.0003) NW,
    // fEfE=(-0.001,-0.001) SW, z@cB=(-0.0003,+0.0005) SE
    private val fakeActivities = listOf(
        Activity(
            id = "1",
            title = "Carrera matutina en el parque",
            date = "10 jun 2026",
            sportType = SportType.Run,
            polyline = "gcpyHfmioVgEgEcBz@cBz@fEfEfEfEz@cBz@cBgEgE",
        ),
        Activity(
            id = "2",
            title = "Vuelta en bici por la montaña",
            date = "8 jun 2026",
            sportType = SportType.Ride,
            polyline = "gcpyHfmioVgEgEgEgEcBz@cBz@fEfEfEfEz@cBz@cBgEgEcBz@",
        ),
        Activity(
            id = "3",
            title = "Caminata vespertina",
            date = "5 jun 2026",
            sportType = SportType.Walk,
            polyline = "gcpyHfmioVrBqA`@e@zAgBrBqA`@e@",
        ),
        Activity(
            id = "4",
            title = "Trail running en el bosque",
            date = "2 jun 2026",
            sportType = SportType.Hike,
            polyline = "gcpyHfmioVrBqAcBz@gEgEgEz@cBfEfErB",
        ),
        Activity(
            id = "5",
            title = "Natación en la piscina olímpica",
            date = "1 jun 2026",
            sportType = SportType.Swim,
            polyline = "gcpyHfmioVgEgEgEgEgEgEfEfEfEfEfEfE",
        ),
    )
}
