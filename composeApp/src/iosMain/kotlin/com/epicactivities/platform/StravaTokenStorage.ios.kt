package com.epicActivities.platform

import com.epicActivities.domain.model.StravaToken
import platform.Foundation.NSUserDefaults

actual class StravaTokenStorage actual constructor() {
    private val defaults = NSUserDefaults.standardUserDefaults

    actual fun save(token: StravaToken) {
        defaults.setObject(token.accessToken, "strava_access_token")
        defaults.setObject(token.refreshToken, "strava_refresh_token")
        defaults.setDouble(token.expiresAt.toDouble(), "strava_expires_at")
    }

    actual fun load(): StravaToken? {
        val accessToken = defaults.stringForKey("strava_access_token") ?: return null
        val refreshToken = defaults.stringForKey("strava_refresh_token") ?: return null
        val expiresAt = defaults.doubleForKey("strava_expires_at").toLong()
        return StravaToken(accessToken, refreshToken, expiresAt)
    }

    actual fun clear() {
        defaults.removeObjectForKey("strava_access_token")
        defaults.removeObjectForKey("strava_refresh_token")
        defaults.removeObjectForKey("strava_expires_at")
    }
}
