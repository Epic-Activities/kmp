package com.epicActivities.domain.repository

import com.epicActivities.domain.model.Activity

interface StravaActivitiesRepository {
    suspend fun getActivities(): Result<List<Activity>>
}
