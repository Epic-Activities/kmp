package com.epicActivities.platform

import platform.Foundation.NSDate

actual fun currentEpochSeconds(): Long = NSDate().timeIntervalSince1970.toLong()
