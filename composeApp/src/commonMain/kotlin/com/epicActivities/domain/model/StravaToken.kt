package com.epicActivities.domain.model

data class StravaToken(
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: Long,
)
