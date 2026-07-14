package com.epicActivities.platform

import com.epicActivities.domain.model.StravaToken

private var inMemoryToken: StravaToken? = null

actual class StravaTokenStorage actual constructor() {
    actual fun save(token: StravaToken) { inMemoryToken = token }
    actual fun load(): StravaToken? = inMemoryToken
    actual fun clear() { inMemoryToken = null }
}
