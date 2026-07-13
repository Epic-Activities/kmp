package com.epicActivities.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class SportType(val displayName: String) {
    Run("Carrera"),
    Ride("Ciclismo"),
    Swim("Natación"),
    Hike("Senderismo"),
    Walk("Caminata"),
    Other("Otro"),
}
