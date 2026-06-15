package com.epicActivities.domain.model

data class Activity(
    val id: String,
    val title: String,
    val date: String,
    val sportType: SportType,
    val polyline: String,
)
