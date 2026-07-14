package com.epicActivities.platform

import com.epicActivities.domain.model.StravaToken

expect class StravaTokenStorage() {
    fun save(token: StravaToken)
    fun load(): StravaToken?
    fun clear()
}
