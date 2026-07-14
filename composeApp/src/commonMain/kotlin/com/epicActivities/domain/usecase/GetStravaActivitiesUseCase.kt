package com.epicActivities.domain.usecase

import com.epicActivities.data.repository.StravaActivitiesRepositoryImpl
import com.epicActivities.domain.model.Activity
import com.epicActivities.domain.repository.StravaActivitiesRepository

class GetStravaActivitiesUseCase(
    private val repository: StravaActivitiesRepository = StravaActivitiesRepositoryImpl(),
) {
    suspend operator fun invoke(): Result<List<Activity>> = repository.getActivities()
}
