package com.epicActivities.platform

import android.content.Context
import android.net.Uri

lateinit var appContext: Context

fun initAppContext(context: Context) {
    appContext = context.applicationContext
}

actual fun readPhotoBytes(uri: String): ByteArray =
    appContext.contentResolver
        .openInputStream(Uri.parse(uri))
        ?.use { it.readBytes() }
        ?: ByteArray(0)
