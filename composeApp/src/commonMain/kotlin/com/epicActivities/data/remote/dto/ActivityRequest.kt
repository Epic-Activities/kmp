package com.epicActivities.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ActivityRequest(
    val polyline: String,
    val distance: String = "",
    val pace: String = "",
    val time: String = "",
)
