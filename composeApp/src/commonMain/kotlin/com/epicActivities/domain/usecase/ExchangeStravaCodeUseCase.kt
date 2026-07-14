package com.epicActivities.domain.usecase

import com.epicActivities.data.repository.StravaAuthRepositoryImpl
import com.epicActivities.domain.model.StravaToken
import com.epicActivities.domain.repository.StravaAuthRepository

class ExchangeStravaCodeUseCase(
    private val repository: StravaAuthRepository = StravaAuthRepositoryImpl(),
) {
    suspend operator fun invoke(code: String): Result<StravaToken> =
        repository.exchangeCode(code)
}
