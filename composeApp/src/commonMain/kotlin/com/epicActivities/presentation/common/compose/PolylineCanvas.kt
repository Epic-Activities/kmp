package com.epicActivities.presentation.common.compose

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epicActivities.domain.util.decodePolyline

@Composable
fun PolylineCanvas(
    polyline: String,
    color: Color,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 3.dp,
) {
    val points = remember(polyline) { decodePolyline(polyline) }
    val textMeasurer = rememberTextMeasurer()

    // FontWeight.Black (900) = thickest weight — bold without increasing size
    val labelStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Black,
        color = Color.White,
    )

    Canvas(modifier = modifier) {
        if (points.size < 2) return@Canvas

        val lats = points.map { it.first }
        val lngs = points.map { it.second }
        val latRange = (lats.max() - lats.min()).coerceAtLeast(0.0001)
        val lngRange = (lngs.max() - lngs.min()).coerceAtLeast(0.0001)
        val minLat = lats.min()
        val minLng = lngs.min()

        val strokePx = strokeWidth.toPx()

        // Pre-measure the widest label so the circle is always large enough for text
        val sampleLayout = textMeasurer.measure("2", labelStyle)
        val innerPad = 5.dp.toPx()
        val endpointRadius = maxOf(
            sampleLayout.size.width / 2f + innerPad,
            sampleLayout.size.height / 2f + innerPad,
            strokePx * 2.5f,
        )

        // Padding must fully contain endpoint circles — endpoints get more space than the plain
        // polyline to keep circles off the canvas edge and give clear breathing room
        val pad = endpointRadius + strokePx + 4.dp.toPx()
        val w = size.width - 2 * pad
        val h = size.height - 2 * pad

        fun toX(lng: Double) = (pad + (lng - minLng) / lngRange * w).toFloat()
        fun toY(lat: Double) = (pad + (1.0 - (lat - minLat) / latRange) * h).toFloat()

        // Draw route polyline
        val path = Path()
        points.forEachIndexed { i, (lat, lng) ->
            val x = toX(lng)
            val y = toY(lat)
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        drawPath(
            path = path,
            color = color,
            style = Stroke(width = strokePx, cap = StrokeCap.Round, join = StrokeJoin.Round),
        )

        // Draw filled circle with centered bold number on top of the polyline
        fun drawEndpoint(x: Float, y: Float, label: String) {
            drawCircle(color = color, radius = endpointRadius, center = Offset(x, y))
            val layout = textMeasurer.measure(label, labelStyle)
            drawText(
                textLayoutResult = layout,
                topLeft = Offset(x - layout.size.width / 2f, y - layout.size.height / 2f),
            )
        }

        drawEndpoint(toX(points.first().second), toY(points.first().first), "1")
        drawEndpoint(toX(points.last().second), toY(points.last().first), "2")
    }
}
