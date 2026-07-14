package com.epicActivities.platform

actual fun currentEpochSeconds(): Long = System.currentTimeMillis() / 1000
