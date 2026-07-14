package com.epicActivities.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StravaActivityDto(
    val id: Long,
    val name: String,
    @SerialName("sport_type") val sportType: String,
    @SerialName("start_date_local") val startDateLocal: String,
    val distance: Double,
    @SerialName("moving_time") val movingTime: Int,
    val map: StravaMapDto? = null,
)

@Serializable
data class StravaMapDto(
    @SerialName("summary_polyline") val summaryPolyline: String? = null,
)
