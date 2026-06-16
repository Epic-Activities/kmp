package com.epicActivities.platform

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readBytes
import platform.Foundation.NSData
import platform.Foundation.NSURL

@OptIn(ExperimentalForeignApi::class)
actual fun readPhotoBytes(uri: String): ByteArray {
    val url = NSURL(string = uri) ?: return ByteArray(0)
    val data = NSData.dataWithContentsOfURL(url) ?: return ByteArray(0)
    val length = data.length.toInt()
    if (length == 0) return ByteArray(0)
    return data.bytes?.readBytes(length) ?: ByteArray(0)
}
