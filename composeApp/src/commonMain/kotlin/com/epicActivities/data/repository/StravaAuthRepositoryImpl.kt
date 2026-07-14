package com.epicActivities.data.repository

import com.epicActivities.data.remote.StravaProxyApi
import com.epicActivities.domain.model.StravaToken
import com.epicActivities.domain.repository.StravaAuthRepository
import com.epicActivities.platform.StravaTokenStorage

class StravaAuthRepositoryImpl(
    private val api: StravaProxyApi = StravaProxyApi(),
    private val storage: StravaTokenStorage = StravaTokenStorage(),
) : StravaAuthRepository {

    override suspend fun exchangeCode(code: String): Result<StravaToken> = runCatching {
        val dto = api.exchangeCode(code)
        val token = StravaToken(dto.accessToken, dto.refreshToken, dto.expiresAt)
        storage.save(token)
        token
    }

    override suspend fun refreshToken(refreshToken: String): Result<StravaToken> = runCatching {
        val dto = api.refreshToken(refreshToken)
        val token = StravaToken(dto.accessToken, dto.refreshToken, dto.expiresAt)
        storage.save(token)
        token
    }

    override fun isConnected(): Boolean = storage.load() != null

    override fun disconnect() = storage.clear()
}
