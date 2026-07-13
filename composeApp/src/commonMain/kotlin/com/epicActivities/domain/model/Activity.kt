package com.epicActivities.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Activity(
    val id: String,
    val title: String,
    val date: String,
    val sportType: SportType,
    val distanceKm: Double,
    val durationSeconds: Int,
    val polyline: String,
)
