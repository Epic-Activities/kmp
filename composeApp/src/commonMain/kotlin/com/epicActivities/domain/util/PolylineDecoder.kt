package com.epicActivities.domain.util

fun decodePolyline(encoded: String): List<Pair<Double, Double>> {
    val result = mutableListOf<Pair<Double, Double>>()
    var index = 0
    var lat = 0
    var lng = 0

    while (index < encoded.length) {
        var shift = 0
        var value = 0
        var byte: Int
        do {
            byte = encoded[index++].code - 63
            value = value or ((byte and 0x1f) shl shift)
            shift += 5
        } while (byte >= 0x20)
        lat += if (value and 1 != 0) (value shr 1).inv() else value shr 1

        if (index >= encoded.length) break

        shift = 0
        value = 0
        do {
            byte = encoded[index++].code - 63
            value = value or ((byte and 0x1f) shl shift)
            shift += 5
        } while (byte >= 0x20)
        lng += if (value and 1 != 0) (value shr 1).inv() else value shr 1

        result.add(Pair(lat / 1e5, lng / 1e5))
    }

    return result
}
