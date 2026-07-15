package com.epicActivities.platform

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.epicActivities.domain.model.StravaToken

actual class StravaTokenStorage actual constructor() {
    private val prefs by lazy {
        val masterKey = MasterKey.Builder(appContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        EncryptedSharedPreferences.create(
            appContext,
            "strava_tokens",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    actual fun save(token: StravaToken) {
        prefs.edit()
            .putString("access_token", token.accessToken)
            .putString("refresh_token", token.refreshToken)
            .putLong("expires_at", token.expiresAt)
            .apply()
    }

    actual fun load(): StravaToken? {
        val accessToken = prefs.getString("access_token", null) ?: return null
        val refreshToken = prefs.getString("refresh_token", null) ?: return null
        val expiresAt = prefs.getLong("expires_at", 0L)
        return StravaToken(accessToken, refreshToken, expiresAt)
    }

    actual fun clear() {
        prefs.edit().clear().apply()
    }
}
