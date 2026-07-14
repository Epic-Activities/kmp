package com.epicActivities.platform

import android.content.Intent
import android.net.Uri

actual class UrlOpener actual constructor() {
    actual fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        appContext.startActivity(intent)
    }
}
