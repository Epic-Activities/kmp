package com.epicActivities.presentation.common.compose

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.epicActivities.domain.util.decodePolyline

@Composable
fun PolylineCanvas(
    polyline: String,
    color: Color,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 3.dp,
) {
    val points = remember(polyline) { decodePolyline(polyline) }

    Canvas(modifier = modifier) {
        if (points.size < 2) return@Canvas

        val lats = points.map { it.first }
        val lngs = points.map { it.second }
        val latRange = (lats.max() - lats.min()).coerceAtLeast(0.0001)
        val lngRange = (lngs.max() - lngs.min()).coerceAtLeast(0.0001)
        val minLat = lats.min()
        val minLng = lngs.min()

        val pad = 8.dp.toPx()
        val w = size.width - 2 * pad
        val h = size.height - 2 * pad

        val path = Path()
        points.forEachIndexed { i, (lat, lng) ->
            val x = (pad + (lng - minLng) / lngRange * w).toFloat()
            val y = (pad + (1.0 - (lat - minLat) / latRange) * h).toFloat()
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }

        drawPath(
            path = path,
            color = color,
            style = Stroke(
                width = strokeWidth.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
            ),
        )
    }
}
