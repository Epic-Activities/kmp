package com.epicActivities.domain.repository

import com.epicActivities.domain.model.StravaToken

interface StravaAuthRepository {
    suspend fun exchangeCode(code: String): Result<StravaToken>
    suspend fun refreshToken(refreshToken: String): Result<StravaToken>
    fun isConnected(): Boolean
    fun disconnect()
}
