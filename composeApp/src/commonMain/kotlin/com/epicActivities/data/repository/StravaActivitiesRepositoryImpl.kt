package com.epicActivities.data.repository

import com.epicActivities.data.remote.StravaProxyApi
import com.epicActivities.data.remote.dto.StravaActivityDto
import com.epicActivities.domain.model.Activity
import com.epicActivities.domain.model.SportType
import com.epicActivities.domain.model.StravaToken
import com.epicActivities.domain.repository.StravaActivitiesRepository
import com.epicActivities.platform.StravaTokenStorage
import com.epicActivities.platform.currentEpochSeconds

class StravaActivitiesRepositoryImpl(
    private val api: StravaProxyApi = StravaProxyApi(),
    private val storage: StravaTokenStorage = StravaTokenStorage(),
    private val authRepo: StravaAuthRepositoryImpl = StravaAuthRepositoryImpl(api, storage),
) : StravaActivitiesRepository {

    override suspend fun getActivities(): Result<List<Activity>> = runCatching {
        val token = validToken()
        api.getActivities(token.accessToken).map { it.toDomain() }
    }

    private suspend fun validToken(): StravaToken {
        val stored = storage.load() ?: error("No Strava token stored")
        return if (stored.expiresAt <= currentEpochSeconds() + 60) {
            authRepo.refreshToken(stored.refreshToken).getOrThrow()
        } else {
            stored
        }
    }
}

private fun StravaActivityDto.toDomain() = Activity(
    id = id.toString(),
    title = name,
    date = formatDate(startDateLocal),
    sportType = mapSportType(sportType),
    distanceKm = distance / 1000.0,
    durationSeconds = movingTime,
    polyline = map?.summaryPolyline.orEmpty(),
)

private fun formatDate(isoDate: String): String {
    val datePart = isoDate.substringBefore("T")
    val parts = datePart.split("-")
    if (parts.size < 3) return datePart
    val (year, month, day) = parts
    val monthName = when (month) {
        "01" -> "ene"; "02" -> "feb"; "03" -> "mar"; "04" -> "abr"
        "05" -> "may"; "06" -> "jun"; "07" -> "jul"; "08" -> "ago"
        "09" -> "sep"; "10" -> "oct"; "11" -> "nov"; "12" -> "dic"
        else -> month
    }
    return "${day.trimStart('0')} $monthName $year"
}

private fun mapSportType(type: String): SportType = when (type) {
    "Run", "VirtualRun", "TrailRun" -> SportType.Run
    "Ride", "VirtualRide", "MountainBikeRide", "GravelRide", "EBikeRide" -> SportType.Ride
    "Swim" -> SportType.Swim
    "Hike" -> SportType.Hike
    "Walk", "NordicSki" -> SportType.Walk
    else -> SportType.Other
}
